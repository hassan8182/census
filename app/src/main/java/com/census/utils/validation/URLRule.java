package com.census.utils.validation;

import android.util.Patterns;

public class URLRule extends RegexRule {


    public URLRule(String message) {
        super(Patterns.WEB_URL.pattern(), message);
    }

}
