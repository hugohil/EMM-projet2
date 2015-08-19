package com.example.clement.emm_project2.util;

import org.jsoup.Jsoup;

/**
 * Created by Clement on 11/08/15.
 */
public class StringUtil {

    public static String capitalize(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

    public static String html2Text(String html) {
        return Jsoup.parse(html).text();
    }

    public static boolean startsWithSameLetter(String s1, String s2) {
        return s1.substring(0,1).equals(s2.substring(0,1));
    }
}
