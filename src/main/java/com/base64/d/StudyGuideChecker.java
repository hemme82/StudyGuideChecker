package com.base64.d;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudyGuideChecker {

    public void checkStudyGuides() {
        int count = 0;
        int totalLinksFound = 0;

        String decodedText = "";
        String iframeSrc = "";
        String titleOfStudyGuide = null;

        Set<String> studyGuideLinks = new HashSet<>();
        Set<String> badLinks = new HashSet<>();

        WriteToFile writeToFile = new WriteToFile();

        for (String studyGuideLink : AllStudyGuideLinks.ALL_STUDY_GUIDES) {
            // Find all links within a Study Guide
            try {
                // Connect to the study guide url
                Document wraperDocument = Jsoup.connect(studyGuideLink).get();

                // Grab the iframe
                Element iframe = wraperDocument.select("iframe").first();

                // Get iframe src url
                iframeSrc = iframe.attr("src");

                // Connect to the iframe source url
                Document studyGuideDocument = Jsoup.connect(iframeSrc).get();

                titleOfStudyGuide = wraperDocument.title();
                System.out.println(titleOfStudyGuide + " " + iframeSrc);

                // Get the script tag info
                Elements scriptTag = studyGuideDocument.getElementsByTag("script");
                String jsScripts = scriptTag.toString();

                // Retrieve the `window.courseData` JavaScript variable.
                // This value will be enclosed in double quotes.
                Pattern courseDataPattern = Pattern.compile("window.courseData = \"(.*)\"");

                Matcher courseDataMatcher = courseDataPattern.matcher(jsScripts);
                String base64String = "";
                while (courseDataMatcher.find()) {
                    System.out.println("--------");

                    base64String = courseDataMatcher.group(1);
                }

                // Put in this try block because one study guide had different script tag layout and threw error.
                try {
                    // Decoding a base64 string and putting into a byte array
                    byte[] decodedArr = Base64.getDecoder().decode(base64String);
                    decodedText = new String(decodedArr, "UTF-8");
                } catch (IllegalArgumentException exc) {
                    exc.printStackTrace();
                    totalLinksFound -= studyGuideLinks.size();
                    System.out.println(titleOfStudyGuide + " not checked because of decoding error");
                }

                // Regex meanings:
                // . => means any character
                // + => means 1 or more
                // * => means 0 or more
                // ? => means 0 or 1
                // Everything in the () is pattern/matcher "group" syntax, then \" is looking for the next quote
                Pattern contentLinkPattern = Pattern.compile("(https://content.infinitecampus.com/sis.*?)\"");

                Matcher contentLinkMatcher = contentLinkPattern.matcher(decodedText);

                while (contentLinkMatcher.find()) {
                    count++;
                    // Some study guide links had '\' added for unknown reason.
                    // This code checks for it and removes it before adding it to the studyGuide.
                    String shorterStudyGuideLink = contentLinkMatcher.group(1).substring(0, contentLinkMatcher.group(1).length() - 1);

                    if (contentLinkMatcher.group(1).substring(contentLinkMatcher.group(1).length() - 1).equals("\\")) {
                        studyGuideLinks.add(shorterStudyGuideLink);
                    } else {
                        studyGuideLinks.add(contentLinkMatcher.group(1));
                    }
                }

                System.out.println(studyGuideLinks.size() + " Unique Campus Community links found");
                // Remove all the false positives that are in JSON of study guide but not visible to front end user.
                studyGuideLinks.removeAll(KnownFalsePositives.WHITE_LIST);
                System.out.println(studyGuideLinks);
                totalLinksFound += studyGuideLinks.size();

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Test all links in a Study Guide
            int response;
            for (String link : studyGuideLinks) {
                try {
                    Connection linkConnection = Jsoup.connect(link).ignoreHttpErrors(true);

                    // Although the `linkContents` is never utilized, it is a
                    // required call. This is because Jsoup expects that `get()`
                    // is called before the `response()` object is available.
                    Document linkContents = linkConnection.get();
                    response = linkConnection.response().statusCode();

                    // Report a bad link if anything other than a "200 OK" response is returned.
                    if (response != 200) {
                        badLinks.add(link);
                        writeToFile.createTxtFile(titleOfStudyGuide, link, studyGuideLink);
                    }
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }

            System.out.println(badLinks.size() + " Broken links: " + badLinks);
            System.out.println(totalLinksFound + " total links checked");

            System.out.println("----------------------------------------------------");
            System.out.println("----------------------------------------------------");

            studyGuideLinks.clear();
        }
        System.out.println(AllStudyGuideLinks.ALL_STUDY_GUIDES.size() + " Total Study Guides");
    }
}
