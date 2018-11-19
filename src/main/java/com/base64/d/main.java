package com.base64.d;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.HttpConnection.Response;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Base64;

public class main {
    public static void main(String[] args) {

        StudyGuideChecker checker = new StudyGuideChecker();
        checker.checkStudyGuides();

//

//        String userName = "dave.hemmesch";
//
//        String password = "";
//        String login = userName + ":" + password;
////        String base64login = new String(Base64.encodeBase64(login.getBytes()));
//        try {
////            Connection.Response res = Jsoup
////                    .connect("https://id.infinitecampus.com/auth/login?url=https%3A%2F%2Fcommunity.infinitecampus.com%2Fnews%2F")
////                    .header("Authorization", "Basic" + login)
////                    .get();
////                    .method(Connection.Method.POST)
////                    .execute();
//
//            Document doc = Jsoup
//                    .connect("https://id.infinitecampus.com/auth/login?url=https%3A%2F%2Fcommunity.infinitecampus.com%2Fnews%2F")
//                    .data("login", "dave.hemmesch")
//                    .data("Password", "")
//                    .get();
//            System.out.println(doc);
//        }
//        catch (IOException e) {
////                e.printStackTrace();
//            }


    }

//        int count = 0;
//        int totalLinksFound = 0;
//        String url3 = "https://content.infinitecampus.com/sis/latest/study-guide/ad-hoc-filters-letters-and-data-viewer";
//        String decodedText = "";
//        String iframeSrc = "";
//        String date = new Date().toString();
//
//        String titleOfStudyGuide = "";
//        Set<String> studyGuideLinks = new HashSet<>();
//        Set<String> badLinks = new HashSet<>();
//
//        WriteToFile writeToFile = new WriteToFile();

//        Set<String> knownFalsePositives = new HashSet<>();
//        knownFalsePositives.add("https://content.infinitecampus.com/sis/latest/simulation/send-a-behavior-message-sc-01-28-01");
//        knownFalsePositives.add("https://content.infinitecampus.com/sis/latest/simulation/schedule-attendance-message-sc-01-24-02");
//        knownFalsePositives.add("https://content.infinitecampus.com/sis/latest/simulation/schedule-a-behavior-message-sc-01-27-02");
//        knownFalsePositives.add("https://content.infinitecampus.com/sis/latest/simulation/create-a-behavior-message-template-sc-01-27-01");
//        knownFalsePositives.add("https://content.infinitecampus.com/sis/latest/simulation/view-individual-student-attendance");
//
//        Set<String> linksFromCommunity = new HashSet<>();
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/academic-planner-system-set-up");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/academic-planner-use-and-management-");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/ad-hoc-filters-letters-and-data-viewer");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/ad-hoc-functions-and-logical-expressions");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/attendance");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/behavior-admin-set-up");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/behavior-data-management-and-reporting");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/behavior-messages-and-letters");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/calendar-rights-user-groups");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/campus-instruction-part-1-the-fundamentals");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/campus-instruction-part-2-grade-book-basics");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/campus-instruction-part-3-advanced-grade-book-and-posting-grades");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/campus-instruction-part-4-campus-learning");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/census---new-personfamily-set-up");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/census---personhousehold-maintenance");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/census-reports");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/flags-and-programs");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/grade-submission-process");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/grading-setup");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/health-module-system-setup");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/health-module-view-and-manage-student-health-information");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/messenger-for-the-end-user");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/tool-rights-user-groups");
//        linksFromCommunity.add("https://content.infinitecampus.com/sis/latest/study-guide/user-account-creation-maintenance-and-reporting");

//        for (String link2 : AllStudyGuideLinks.ALL_STUDY_GUIDES) {
//            try {
////
////          Connect to the study guide url
//                Document doc3 = Jsoup.connect(link2).get();
//
////            grab the iframe
//                Element iframe = doc3.select("iframe").first();
//
////            get iframe scr url
//                iframeSrc = iframe.attr("src");
//
////            connect to the iframe source url
//                Document doc = Jsoup.connect(iframeSrc).get();
//
//                titleOfStudyGuide = doc3.title();
//                System.out.println(titleOfStudyGuide + " " + iframeSrc);
//
////            get the script tag info
//                Elements scriptTag = doc.getElementsByTag("script");
//                String jsScripts = scriptTag.toString();
//
////            match any string of characters over 100 in length.
//                String patternString = "(\\w{100,}.+)";
//
//                Pattern pattern = Pattern.compile(patternString);
//
//                Matcher matcher = pattern.matcher(jsScripts);
////            System.out.println(matcher);
//                String foundBase64String = "";
//                while (matcher.find()) {
//                    System.out.println("--------");
//
//                    foundBase64String = matcher.group(1);
//                }
//                //reduced the giant string by 2 because it included the " and the ;
//                String trimFoundBase64String = foundBase64String.substring(0, (foundBase64String.length() - 2));
//
//                //put in this try block because one study guide had different script tag layout and threw error.
//                try {
//                    // decoding a base64 string and putting into a byte array
//                    byte[] decodedArr = Base64.getDecoder().decode(trimFoundBase64String);
//                    decodedText = new String(decodedArr, "UTF-8");
//                    //System.out.println("decoded base64: " + decodedText);
//                } catch
//                (IllegalArgumentException exc) {
//                    exc.printStackTrace();
//                    totalLinksFound -= studyGuideLinks.size();
//                    System.out.println(titleOfStudyGuide + " not checked because of decoding error");
//                }
//
//                //      regex meaning - . means any character. + means 1 or more, * means 0 or more. ? means 0 or 1
//                //        everything in the () is pattern/matcher "group" syntax. then \" is looking for the next quote
//                String patternString2 = "(https://content.infinitecampus.com/sis.*?)\"";
//
//                Pattern pattern2 = Pattern.compile(patternString2);
//
//
//                Matcher matcher2 = pattern2.matcher(decodedText);
//                //System.out.println("This is the matcher " + matcher2);
//
//
//                while (matcher2.find()) {
//                    count++;
//                    //some study guide links had '\' added for unknown reason. this code checks for it and removes it before adding it to the studyGuide.
//                    String shorterStudyGuideLink = matcher2.group(1).substring(0, matcher2.group(1).length() - 1);
////                System.out.println(shorterStudyGuideLink);
//
//                    if (matcher2.group(1).substring(matcher2.group(1).length() - 1).equals("\\")) {
//                        studyGuideLinks.add(shorterStudyGuideLink);
//                    } else {
//                        studyGuideLinks.add(matcher2.group(1));
//                    }
//                }
//
//                System.out.println(studyGuideLinks.size() + " Unique Campus Community links found");
//                //remove all the false positives that are in JSON of study guide but not visible to front end user.
//                studyGuideLinks.removeAll(KnownFalsePositives.BLACK_LIST);
//                System.out.println(studyGuideLinks);
//                totalLinksFound += studyGuideLinks.size();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            int response;
//            for (String link : studyGuideLinks) {
////                Document doc2 = (Document) Jsoup.connect(link).response();
//                try {
//                    Connection connection = Jsoup.connect(link).ignoreHttpErrors(true);
//
//
//                    Document doc2 = connection.get();
//                    response = connection.response()
//                            .statusCode();
//
//                    if (response >= 400) {
//                        badLinks.add(link);
//                        writeToFile.createTxtFile(titleOfStudyGuide, link,  link2);
////                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
////                        try {
////                            FileWriter fileWriter = new FileWriter("DeadLinks.txt", true);
////                            PrintWriter printWriter = new PrintWriter(fileWriter);
////
////                            printWriter.println(titleOfStudyGuide);
////                            printWriter.println("Link to Study Guide: " + link2);
////                            printWriter.println("This link is broken: " + link);
////                            printWriter.println(date);
////                            printWriter.println("---------------------------------------");
////
////
////                            printWriter.close();
////                        }
////                        catch (IOException exception) {
////                            out.println("ERROR");
////                            exception.printStackTrace();
////                        }
//                    }
//                } catch (IOException ie) {
//                    ie.printStackTrace();
//                }
//            }
//
//            System.out.println(badLinks.size() + " Broken links: " + badLinks);
//            System.out.println(totalLinksFound + " total links checked");
//
//            System.out.println("----------------------------------------------------");
//            System.out.println("----------------------------------------------------");
//
//            studyGuideLinks.clear();
//        }
//        System.out.println(AllStudyGuideLinks.ALL_STUDY_GUIDES.size() + " Total Study Guides");
//    }


}



