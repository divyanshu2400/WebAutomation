package com.truecodes.utils;


public class ConfigReader {
    private static final String baseURL = "https://automationexercise.com/";
    private static final String browser = "chrome";

    public static String getBrowser() {
        return browser;
    }

    public static String getBaseURL() {
        return baseURL;
    }
}