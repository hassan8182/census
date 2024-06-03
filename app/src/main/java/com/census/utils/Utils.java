package com.census.utils;


import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class Utils {
    public static String encodeString(String stringToEncode) {
        String encodedString = Base64.encodeToString(stringToEncode.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return encodedString.trim().replaceAll("\n", "").replaceAll(" ", "");
    }
}
