package com.census.utils.validation;

import java.util.regex.Pattern;

public class RegexRule extends Rule {

    public static String CONSECUTIVE_DOT_REGEX = "(.*?[.]{2}).*";
    public static String SPECIAL_CHARACTERS_REGEX = "^[a-zA-Z0-9._]*$";
    //public static String SPECIAL_CHARACTERS_REGEX = "^[_]?[a-zA-Z0-9]+([_.-]?[a-zA-Z0-9])*$";
    public static String NUMBERS_ONLY_REGEX = "^[0-9._]*$";
    public static String PASSWORD_REGEX = "^[a-zA-Z0-9@#&_$]*$";
//    public static String URL_REGEX = "((http|https)://)?[a-zA-Z]\\w*(\\.\\w+)+(/\\w*(\\.\\w+)*)*(\\?.+)*";
//    public static String URL_REGEX = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|co|pk|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\’\\\\\\+&amp;%\\$#\\=~_\\-]+))*$"; // from iOS: worked for urls with http etc only
    public static String URL_REGEX = "^((http|https|ftp)://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|co|pk|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\’\\\\\\+&amp;%\\$#\\=~_\\-]+))*$";
    public static String EMAIL_REGEX = "^[a-z0-9](\\.?[a-z0-9_-]){0,}@[a-z0-9-]+\\.([a-z]{1,6}\\.)?[a-z]{2,6}$";


    private String regex;
    private boolean applyReverse;

    public RegexRule(String regex, String message) {
        this.message = message;
        this.regex = regex;
        this.applyReverse = false;
    }

    public RegexRule(String regex, String message, boolean applyReverse) {
        this.message = message;
        this.regex = regex;
        this.applyReverse = applyReverse;
    }


    @Override
    public boolean validate(String value) {
        boolean isValid = Pattern.compile(regex).matcher(value).matches();
        if (applyReverse)
            isValid = !isValid;
        return isValid;
    }
}
