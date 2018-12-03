package com.base64.d;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {

        StudyGuideChecker checker = new StudyGuideChecker();
        checker.checkStudyGuides();
//        String communityURL = "https://www.cnn.com/";
////
//////        Connection linkConnection = Jsoup.connect(communityURL);
////        Document communityLogin = Jsoup.parse(communityURL);
////        System.out.println(communityLogin);
////        Element link = communityLogin.select("a").first();
////        System.out.println(link);


//        Elements content = communityLogin.getElementsByClass("breaking-news__background");
//        System.out.println(content);

//        Connection.Response loginForm = Jsoup.connect(String.valueOf(communityLogin))
//                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0")
//                .method(Connection.Method.POST)
//                .get
//                .timeout(3000)
//                .data("dave.hemmesch", signin-form_login)
//                .data("password", password)
//                .execute();
//
//        System.out.println(loginForm.cookies());







//            communityLogin.body() = (Element) Jsoup.connect("login Site URL")
//                    .method(Connection.Method.GET)
//                    .timeout(10000)
//                    .execute();
    }

//        String sessionID = res.cookie("SESSION ID for site");

}



