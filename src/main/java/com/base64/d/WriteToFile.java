package com.base64.d;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.out;


public class WriteToFile {

    String date = new Date().toString();

    public void createTxtFile(String titleOfStudyGuide, String link, String link2) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            FileWriter fileWriter = new FileWriter("DeadLinks.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("\n");
            printWriter.println("---------------------------------------");
            printWriter.println(date);
            printWriter.println(titleOfStudyGuide);
            printWriter.println("Link to Study Guide: " + link2);
            printWriter.println("This link is broken: " + link);
            printWriter.println("---------------------------------------");


            printWriter.close();
        } catch (
                IOException exception) {
            out.println("ERROR");
            exception.printStackTrace();
        }
    }

    public void writeTotalCount(String totalLinksFound){
        try {
            Files.write(Paths.get("DeadLinks.txt"), (String.format("%s total links checked\n", totalLinksFound)).getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
    }
}
