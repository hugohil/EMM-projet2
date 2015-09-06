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
        return s1.substring(0, 1).equals(s2.substring(0, 1));
    }

    public static String formatDuration(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;

        return String.format("%02dh%02d", hours, minutes);
    }

    public static String truncatePrice(Float price) {
        String result = new Double(price).toString();
        if(result.length() > 4) {
            result = result.substring(0, 5);
        }
        return result;
    }



}
