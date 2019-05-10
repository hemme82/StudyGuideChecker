package com.base64.d;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudyGuideChecker {

    private int totalLinksFound = 0;

    public int getTotalLinksFound() {
        return totalLinksFound;
    }

    public void checkStudyGuides() throws IOException {
        int count = 0;


        String decodedText = "";
        String iframeSrc;
        //why null here?
        String titleOfStudyGuide = null;

        Set<String> studyGuideLinks = new HashSet<>();
        Set<String> badLinks = new HashSet<>();

        WriteToFile writeToFile = new WriteToFile();

        for (String studyGuideLink : AllStudyGuideLinks.ALL_STUDY_GUIDES) {
            try {

                //Connect to the study guide url
                Document wrapperDocument = Jsoup.connect(studyGuideLink).get();

                //Grab the iframe
                Element iframe = wrapperDocument.select("iframe").first();

                //Get iframe scr url
                iframeSrc = iframe.attr("src");

                //Connect to the iframe source url
                Document studyGuideDocument = Jsoup.connect(iframeSrc).get();

                titleOfStudyGuide = wrapperDocument.title();
                System.out.println(titleOfStudyGuide + " " + iframeSrc);

                //Get the script tag info
                Elements scriptTag = studyGuideDocument.getElementsByTag("script");
                String jsScripts = scriptTag.toString();

                //Match any string of characters over 100 in length.
                Pattern courseDataPattern = Pattern.compile("(\\w{100,}.+)");

                Matcher courseDataMatcher = courseDataPattern.matcher(jsScripts);

                String base64String = "";
                while (courseDataMatcher.find()) {
                    System.out.println("--------");

                    base64String = courseDataMatcher.group(1);
                }
                //Reduced the giant string by 2 because it included the " and the ;
                String trimFoundBase64String = base64String.substring(0, (base64String.length() - 2));

                //Put in this try block because one study guide had different script tag layout and threw error.
                try {
                    // Decoding a base64 string and putting into a byte array
                    byte[] decodedArr = Base64.getDecoder().decode(trimFoundBase64String);
                    decodedText = new String(decodedArr, "UTF-8");
                    writeToFile.createJSONTxtFile(decodedText);
                    //System.out.println("decoded base64: " + decodedText);
                } catch
                (IllegalArgumentException exc) {
                    exc.printStackTrace();
                    totalLinksFound -= studyGuideLinks.size();
                    System.out.println(titleOfStudyGuide + " not checked because of decoding error");
                }


                // Regex meanings:
                // . => means any character
                // + => means 1 or more
                // * => means 0 or more
                // ? +> means 0 or 1
                // Everything in the () is courseDataPattern/courseDataMatcher "group" syntax. then \" is looking for the next quote
                Pattern contentLinkPattern = Pattern.compile("(https://content.infinitecampus.com/sis.*?)\"");

                Matcher contentLinkMatcher = contentLinkPattern.matcher(decodedText);
                //System.out.println("This is the courseDataMatcher " + contentLinkMatcher);


                while (contentLinkMatcher.find()) {
                    count++;
                    //Some study guide links had '\' added for unknown reason. this code checks for it and removes it before adding it to the studyGuide.
                    String shorterStudyGuideLink = contentLinkMatcher.group(1).replaceAll("\\\\+$", "");
                    //Links are good because regex replace all removes "\" if there.
                    studyGuideLinks.add(shorterStudyGuideLink);

                }

                System.out.println(studyGuideLinks.size() + " Unique Campus Community links found");
                //Remove all the false positives that are in JSON of study guide but not visible to front end user.
                studyGuideLinks.removeAll(KnownFalsePositives.BLACK_LIST);
                System.out.println(studyGuideLinks);
                totalLinksFound += studyGuideLinks.size();

            } catch (IOException e) {
                e.printStackTrace();
            }


            int response;
            for (String link : studyGuideLinks) {

                try {
                    Connection linkConnection = Jsoup.connect(link).ignoreHttpErrors(true);

                    // Although the `linkContents` is never utilized, it is a
                    // required call. This is because Jsoup expects that `get()`
                    // is called before the `response()` object is available.
                    Document linkContents = linkConnection.get();
                    response = linkConnection.response()
                            .statusCode();
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
            System.out.println(getTotalLinksFound() + " total links checked");


            System.out.println("----------------------------------------------------");
            System.out.println("----------------------------------------------------");

            studyGuideLinks.clear();
        }
        System.out.println(AllStudyGuideLinks.ALL_STUDY_GUIDES.size() + " Total Study Guides");

        String totalLinksFoundAsString = Integer.toString(totalLinksFound);
//        totalLinksFoundAsString = totalLinksFound.to;

        writeToFile.writeTotalCount(totalLinksFoundAsString);

    }


}

