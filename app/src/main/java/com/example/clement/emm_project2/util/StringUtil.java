package com.example.clement.emm_project2.util;

/**
 * Created by Clement on 11/08/15.
 */
public class StringUtil {

    public static String capitalize(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

    public static String html2Text(String html) {
        // Here html chars like &amp; won't be escaped
        // Use Jsoup instead! Simple as : Jsoup.parse(html).text();
        return html.replaceAll("\\<.*?>","");
    }
}
