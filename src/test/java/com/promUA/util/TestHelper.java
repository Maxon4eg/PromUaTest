package com.promUA.util;


import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class TestHelper {
    public static String generateTestMail() {
        return AppProperties.getDefaultEmailName() + "+" +
                RandomStringUtils.randomAlphabetic(5)
                + "@" + AppProperties.getDefaultEmailDomain();
    }

    public static String decodeHtml(String text) {
        try {
            return java.net.URLDecoder.decode(text, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
