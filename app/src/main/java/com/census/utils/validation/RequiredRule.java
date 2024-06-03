package com.census.utils.validation;

import android.text.TextUtils;

public class RequiredRule extends Rule {


    public RequiredRule(String message) {
        this.message=message;
    }


    @Override
    public boolean validate(String value) {
        return !TextUtils.isEmpty(value);
    }
}
