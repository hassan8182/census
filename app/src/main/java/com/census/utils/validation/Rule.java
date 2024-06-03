package com.census.utils.validation;

public abstract class Rule {
    // String that holds error message.
    protected String message;

    /**
     * Validates text as string.
     *
     * @param value String of text to be validated.
     * @return boolean value. True if validation is successful; False if validation fails.
     */
    public abstract boolean validate(String value);

    /**
     * Displays error message of a field that has failed validation.
     *
     * @return String of error message.
     */
    public String error() {
        return message;
    }
}
