package com.census.utils.validation;


import java.util.ArrayList;
import java.util.List;

public final class Validator {
    public final static short MIN_AGE_LIMIT = 12;
    public final static short MAX_AGE_LIMIT = 99;
    private final static short MIN_NAME_LENGTH = 3;
    private final static short MAX_NAME_LENGTH = 64;
    private final static short MAX_ABOUTME_LENGTH = 250;
    private final static short MIN_PASSWORD_LENGTH = 6;
    private final static short MAX_PASSWORD_LENGTH = 25;

    private Validator() {
    }

    /**
     * It validates each rule
     *
     * @param value    string on which we want to apply validation rules
     * @param ruleList list of rules that we want to apply
     * @return string error message. null if no error, error message otherwise
     */
    public static String validate(String value, List<Rule> ruleList) {
        for ( Rule rule : ruleList) {
            boolean isValid = rule.validate(value);
            if (!isValid)
                return rule.error();
        }

        return null;
    }

//    /**
//     * Validate a Name string
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for a failed rule, null for successful validation
//     */
//
//    public static String applyNameRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_name)));
//        ruleList.add(new LengthRule(MIN_NAME_LENGTH, true, resourceProvider.getString(R.string.error_name_length)));
//        ruleList.add(new LengthRule(MAX_NAME_LENGTH, false, resourceProvider.getString(R.string.error_name_length)));
//        ruleList.add(new RegexRule(RegexRule.NUMBERS_ONLY_REGEX, resourceProvider.getString(R.string.error_valid_name), true));
//
//        return validate(value, ruleList);
//
//    }
//
//    /**
//     * Validate a Name string
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for a failed rule, null for successful validation
//     */
//
//    public static String applyAboutMeRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new LengthRule(MAX_ABOUTME_LENGTH, false, resourceProvider.getString(R.string.error_aboutme_length)));
//        return validate(value, ruleList);
//
//    }
//
//
//    /**
//     * Validate a Username string
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for a failed rule, null for successful validation
//     */
//    public static String applyUsernameRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_empty_username)));
//        ruleList.add(new LengthRule(MIN_NAME_LENGTH, true, resourceProvider.getString(R.string.error_username_length)));
//        ruleList.add(new LengthRule(MAX_NAME_LENGTH, false, resourceProvider.getString(R.string.error_username_length)));
//        ruleList.add(new RegexRule(RegexRule.NUMBERS_ONLY_REGEX, resourceProvider.getString(R.string.error_username_missing_alphabets), true));
//        ruleList.add(new RegexRule(RegexRule.CONSECUTIVE_DOT_REGEX, resourceProvider.getString(R.string.error_username_dots), true));
//        ruleList.add(new RegexRule(RegexRule.SPECIAL_CHARACTERS_REGEX, resourceProvider.getString(R.string.error_username_format), false));
//
//        return validate(value, ruleList);
//
//    }
//
//    /**
//     * Validate a Password string
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for a failed rule, null for successful validation
//     */
//    public static String applyPasswordRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_empty_password)));
//        ruleList.add(new LengthRule(MIN_PASSWORD_LENGTH, true, resourceProvider.getString(R.string.error_password_length)));
//        ruleList.add(new LengthRule(MAX_PASSWORD_LENGTH, false, resourceProvider.getString(R.string.error_password_length)));
//        ruleList.add(new RegexRule(RegexRule.PASSWORD_REGEX, resourceProvider.getString(R.string.error_password_format)));
//
//        return validate(value, ruleList);
//
//    }
//
//    /**
//     * Validate if two strings match
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for failure, null for successful validation
//     */
//    public static String applyConfirmPasswordRules(String value, String originalString, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_empty_confirm_password)));
//        ruleList.add(new ConfirmRule(originalString, resourceProvider.getString(R.string.error_password_mismatch)));
//
//        return validate(value, ruleList);
//
//    }
//
//    /**
//     * Validate the age
//     * Note: date format should be in 'yyyy-MM-dd' format
//     *
//     * @param value            date string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for failure, null for successful validation
//     */
//    public static String applyAgeRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_age)));
//        ruleList.add(new DateRule(resourceProvider.getString(R.string.error_age_invalid)));
//        ruleList.add(new AgeLimitRule(MIN_AGE_LIMIT, MAX_AGE_LIMIT, resourceProvider.getString(R.string.error_age_limit)));
//
//        return validate(value, ruleList);
//    }
//
//    /**
//     * Validate the Email string
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for failure, null for successful validation
//     */
//    public static String applyEmailRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_empty_email)));
//        ruleList.add(new EmailRule(resourceProvider.getString(R.string.error_email)));
//
//        return validate(value, ruleList);
//    }
//
//    /**
//     * Validate the Phone number
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for failure, null for successful validation
//     */
//    public static String applyPhoneNumberRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_empty_phone)));
//        ruleList.add(new PhoneNumberRule(resourceProvider.getString(R.string.error_phone)));
//        ruleList.add(new LengthRule(6, true, resourceProvider.getString(R.string.error_phone)));
//
//        return validate(value, ruleList);
//    }
//
//    /**
//     * Validate the URL string
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for failure, null for successful validation
//     */
    public static String applyURLRules(String value) {
        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(new URLRule("Invalid URL"));
        ruleList.add(new RegexRule(RegexRule.URL_REGEX, "Invalid URL"));

        return validate(value, ruleList);
    }

//    /**
//     * Validate the Verification Code
//     *
//     * @param value            string on which we want to apply validation rules
//     * @param resourceProvider helper to get string messages from resources
//     * @return string error message for failure, null for successful validation
//     */
//    public static String applyVerificationCodeRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_code)));
//        ruleList.add(new LengthRule(BaseVerifyCodeViewModel.CODE_LENGTH, true, resourceProvider.getString(R.string.error_code)));
//
//        return validate(value, ruleList);
//    }
//
//    public static String applyRecommendationRules(String value, ResourceProvider resourceProvider) {
//        List<Rule> ruleList = new ArrayList<>();
//        ruleList.add(new RequiredRule(resourceProvider.getString(R.string.error_empty_feedback)));
//        ruleList.add(new LengthRule(10, true, resourceProvider.getString(R.string.error_feedback_length)));
//        return validate(value, ruleList);
//    }
//
    public static String applyRequiredRule(String value, String errorMessage) {
        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(new RequiredRule(errorMessage));
        return validate(value, ruleList);

    }
}
