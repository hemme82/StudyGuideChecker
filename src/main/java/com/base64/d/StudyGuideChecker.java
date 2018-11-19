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
    int count = 0;
    public int totalLinksFound = 0;
    String url3 = "https://content.infinitecampus.com/sis/latest/study-guide/ad-hoc-filters-letters-and-data-viewer";
    String decodedText = "";
    String iframeSrc = "";
    String date = new Date().toString();

    String titleOfStudyGuide = "";
    Set<String> studyGuideLinks = new HashSet<>();
    Set<String> badLinks = new HashSet<>();

    WriteToFile writeToFile = new WriteToFile();

    public void checkStudyGuides() {

        for (String link2 : AllStudyGuideLinks.ALL_STUDY_GUIDES) {
            try {
//
//          Connect to the study guide url
                Document doc3 = Jsoup.connect(link2).get();

//            grab the iframe
                Element iframe = doc3.select("iframe").first();

//            get iframe scr url
                iframeSrc = iframe.attr("src");

//            connect to the iframe source url
                Document doc = Jsoup.connect(iframeSrc).get();

                titleOfStudyGuide = doc3.title();
                System.out.println(titleOfStudyGuide + " " + iframeSrc);

//            get the script tag info
                Elements scriptTag = doc.getElementsByTag("script");
                String jsScripts = scriptTag.toString();

//            match any string of characters over 100 in length.
                String patternString = "(\\w{100,}.+)";

                Pattern pattern = Pattern.compile(patternString);

                Matcher matcher = pattern.matcher(jsScripts);
//            System.out.println(matcher);
                String foundBase64String = "";
                while (matcher.find()) {
                    System.out.println("--------");

                    foundBase64String = matcher.group(1);
                }
                //reduced the giant string by 2 because it included the " and the ;
                String trimFoundBase64String = foundBase64String.substring(0, (foundBase64String.length() - 2));

                //put in this try block because one study guide had different script tag layout and threw error.
                try {
                    // decoding a base64 string and putting into a byte array
                    byte[] decodedArr = Base64.getDecoder().decode(trimFoundBase64String);
                    decodedText = new String(decodedArr, "UTF-8");
                    //System.out.println("decoded base64: " + decodedText);
                } catch
                (IllegalArgumentException exc) {
                    exc.printStackTrace();
                    totalLinksFound -= studyGuideLinks.size();
                    System.out.println(titleOfStudyGuide + " not checked because of decoding error");
                }

                //      regex meaning - . means any character. + means 1 or more, * means 0 or more. ? means 0 or 1
                //        everything in the () is pattern/matcher "group" syntax. then \" is looking for the next quote
                String patternString2 = "(https://content.infinitecampus.com/sis.*?)\"";

                Pattern pattern2 = Pattern.compile(patternString2);


                Matcher matcher2 = pattern2.matcher(decodedText);
                //System.out.println("This is the matcher " + matcher2);


                while (matcher2.find()) {
                    count++;
                    //some study guide links had '\' added for unknown reason. this code checks for it and removes it before adding it to the studyGuide.

                    String shorterStudyGuideLink = matcher2.group(1).replaceAll("\\\\+$", "");
//                            .substring(0, matcher2.group(1).length() - 1);
//                System.out.println(shorterStudyGuideLink);
//                  links are good because regex replace all removes "\" if there.
                    studyGuideLinks.add(shorterStudyGuideLink);

                }

                System.out.println(studyGuideLinks.size() + " Unique Campus Community links found");
                //remove all the false positives that are in JSON of study guide but not visible to front end user.
                studyGuideLinks.removeAll(KnownFalsePositives.BLACK_LIST);
                System.out.println(studyGuideLinks);
                totalLinksFound += studyGuideLinks.size();

            } catch (IOException e) {
                e.printStackTrace();
            }


            int response;
            for (String link : studyGuideLinks) {
//                Document doc2 = (Document) Jsoup.connect(link).response();
                try {
                    Connection connection = Jsoup.connect(link).ignoreHttpErrors(true);


                    Document doc2 = connection.get();
                    response = connection.response()
                            .statusCode();

                    if (response >= 400) {
                        badLinks.add(link);
                        writeToFile.createTxtFile(titleOfStudyGuide, link, link2);
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

        String totalLinksFoundAsString = Integer.toString(totalLinksFound);
//        totalLinksFoundAsString = totalLinksFound.to;

        writeToFile.writeTotalCount(totalLinksFoundAsString);
    }
}

