package com.base64.d;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;




public class WriteToFile {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH;mm");
    private String date = dateFormat.format(new Date());

    public void createTxtFile(String titleOfStudyGuide, String link, String link2) {
        try {
            FileWriter fileWriter = new FileWriter("DeadLinks " + date + ".txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("\n");
            printWriter.println("---------------------------------------");

            printWriter.println(titleOfStudyGuide);
            printWriter.println("Link to Study Guide: " + link2);
            printWriter.println("This link is broken: " + link);
            printWriter.println(date);
            printWriter.println("---------------------------------------");


            printWriter.close();
        } catch (
                IOException exception) {
            System.out.println("Bad links txt file ERROR");
            exception.printStackTrace();

        }
    }

    public void createJSONTxtFile(String decodedText) throws IOException {
        try{
            FileWriter fileWriter = new FileWriter("JSON2.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(decodedText);

        } catch (
            IOException exception2) {
                System.out.println("JSON txt file ERROR");
                exception2.printStackTrace();
            }
        }


    public void writeTotalCount(String totalLinksFound)throws IOException{
        try {
            Files.write(Paths.get("DeadLinks " + date + ".txt"), (String.format("Total links " +
                    "checked %s\n", totalLinksFound) + " Number of Study Guides: " + AllStudyGuideLinks.ALL_STUDY_GUIDES.size()).getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }

    }


}
