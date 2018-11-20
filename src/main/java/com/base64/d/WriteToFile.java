package com.base64.d;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class WriteToFile {

    public void createTxtFile(String titleOfStudyGuide, String link, String link2) {
        String date = new Date().toString();

        try {
            FileWriter fileWriter = new FileWriter("DeadLinks.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(titleOfStudyGuide);
            printWriter.println("Link to Study Guide: " + link2);
            printWriter.println("This link is broken: " + link);
            printWriter.println(date);
            printWriter.println("---------------------------------------");

            printWriter.close();
        } catch (IOException exception) {
            System.out.println("ERROR");
            exception.printStackTrace();
        }
    }
}
