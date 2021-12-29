package com.java.project3.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class StringUtil {
    public static String covertStringToUrl(String value) {
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d")
                    .replaceAll("\\.","-")
                    .replaceAll(" ", "-")
                    .replaceAll("/","-")
                    .replaceAll("\\\\","-")
                    .replaceAll(";","-")
                    .replaceAll("\\[", "-")
                    .replaceAll("]", "-");
        } catch (Exception ex) {
           System.out.println(ex.getMessage());
        }
        return value;
    }
}
