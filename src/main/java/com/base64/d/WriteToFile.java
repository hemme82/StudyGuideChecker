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

import java.sql.Timestamp;

import static java.lang.System.out;


public class WriteToFile {



    public void createTxtFile(String titleOfStudyGuide, String link, String link2) {
        //String date = new Date().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String date = dateFormat.format(new Date());
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
