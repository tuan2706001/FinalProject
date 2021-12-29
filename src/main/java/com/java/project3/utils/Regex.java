package com.java.project3.utils;

public class Regex {

    public static Boolean isEmailFormat(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if (email.matches(regex)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static Boolean isPhoneFormat(String phone) {

        if (phone.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")) {
            return true;
        }
        else {
            return false;
        }

    }
}
