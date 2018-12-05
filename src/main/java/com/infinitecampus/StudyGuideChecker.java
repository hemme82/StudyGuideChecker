package com.infinitecampus;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudyGuideChecker {

    private static Logger log = LoggerFactory.getLogger(StudyGuideChecker.class);

    public static void main(String[] args) {

        Properties properties = loadProperties();

        List<String> studyGuideUrls = Arrays.asList(
                properties.getProperty("study-guide-urls").split(","));
        List<String> knownBadLinks = Arrays.asList(
                properties.getProperty("known-bad-links").split(","));

        // Retrieve all the content links from all the Study Guides
        Map<String, List<String>> allLinksToCheck = new HashMap<>();
        for (String studyGuideUrl : studyGuideUrls) {
            List<String> links = getLinksInStudyGuide(studyGuideUrl);
            // In order to have a single list of all links, and also keep track
            // of the study guide(s) using the link, invert these collection,
            // and have each link point to a List of Study Guide links.
            for (String link : links) {
                // Only process links that are not known to be bad.
                if (!knownBadLinks.contains(link)) {
                    // Check to see if the link is already in the collection, and
                    // if so, simply add the studyGuideUrl to the List
                    if (allLinksToCheck.containsKey(link)) {
                        List<String> studyGuidesUsingThisLink = allLinksToCheck.get(link);
                        studyGuidesUsingThisLink.add(studyGuideUrl);
                    }
                    else {
                        List<String> studyGuidesUsingThisLink = new ArrayList<>();
                        studyGuidesUsingThisLink.add(studyGuideUrl);
                        allLinksToCheck.put(link, studyGuidesUsingThisLink);
                    }
                }
            }
        }

        // Validate each link
        Map<String, List<String>> foundBadLinks = new HashMap<>();
        for (Map.Entry<String, List<String>> linkToCheck : allLinksToCheck.entrySet()) {
            String link = linkToCheck.getKey();
            Connection linkConnection = Jsoup.connect(link).ignoreHttpErrors(true);

            // Although the `linkContents` is never utilized, it is a
            // required call. This is because Jsoup expects that `get()`
            // is called before the `response()` object is available.
            Document linkContents = null;
            try {
                linkContents = linkConnection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int response = linkConnection.response().statusCode();
            // Report a bad link if anything other than a "200 OK" response is returned.
            if (response != 200) {
                foundBadLinks.put(link, linkToCheck.getValue());
            }
        }

        // Report, indicating each bad link, and the associated study guide(s)
        for (Map.Entry<String, List<String>> badLink : foundBadLinks.entrySet()) {
            log.info("Bad Link: " + badLink.getKey());
            for (String studyGuideUrl : badLink.getValue()) {
                log.info("    Found in Study Guide: " + studyGuideUrl);
            }
        }
    }

    /**
     * Retrieve the Properties file for this application.
     * @return
     */
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Given a URL to a Study Guide, find all of the links to content, and
     * return them as a List.
     * @param studyGuideUrl
     * @return
     */
    private static List<String> getLinksInStudyGuide(String studyGuideUrl) {
        List<String> linksInStudyGuide = new ArrayList<>();

        // Connect to the study guide url
        Document wrapperDocument = null;
        try {
            wrapperDocument = Jsoup.connect(studyGuideUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Grab the iframe
        Element iframe = wrapperDocument.select("iframe").first();

        // Get iframe scr url
        String iframeSrc = iframe.attr("src");

        // Connect to the iframe source url
        Document studyGuideDocument = null;
        try {
            studyGuideDocument = Jsoup.connect(iframeSrc).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String titleOfStudyGuide = wrapperDocument.title();

        //Get the script tag info
        Elements scriptTag = studyGuideDocument.getElementsByTag("script");
        String jsScripts = scriptTag.toString();

        //Match any string of characters over 100 in length.
        Pattern courseDataPattern = Pattern.compile("(\\w{100,}.+)");

        Matcher courseDataMatcher = courseDataPattern.matcher(jsScripts);

        String base64String = null;
        while (courseDataMatcher.find()) {
            base64String = courseDataMatcher.group(1);
        }
        //Reduced the giant string by 2 because it included the " and the ;
        String trimFoundBase64String = base64String.substring(0, (base64String.length() - 2));

        // Put in this try block because one study guide had different script tag layout and threw error.
        String decodedText = null;
        try {
            // Decoding a base64 string and putting into a byte array
            byte[] decodedArr = Base64.getDecoder().decode(trimFoundBase64String);
            decodedText = new String(decodedArr, "UTF-8");
            //System.out.println("decoded base64: " + decodedText);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Regex meanings:
        // . => means any character
        // + => means 1 or more
        // * => means 0 or more
        // ? +> means 0 or 1
        // Everything in the () is courseDataPattern/courseDataMatcher "group" syntax. then \" is looking for the next quote
        Pattern contentLinkPattern = Pattern.compile("(https://content.infinitecampus.com/sis.*?)\"");

        Matcher contentLinkMatcher = contentLinkPattern.matcher(decodedText);

        while (contentLinkMatcher.find()) {
            // Some study guide links had '\' added for unknown reason. this code checks for it and removes it before adding it to the studyGuide.
            String shorterStudyGuideLink = contentLinkMatcher.group(1).replaceAll("\\\\+$", "");
            // Links are good because regex replace all removes "\" if there.
            linksInStudyGuide.add(shorterStudyGuideLink);
        }

        return linksInStudyGuide;
    }
}
