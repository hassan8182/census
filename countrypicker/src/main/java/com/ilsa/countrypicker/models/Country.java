package com.ilsa.countrypicker.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Country {

    // region Variables
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("dialCode")
    private String dialCode;
    private int flag;
    // endregion

    public Country() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getFlagEmoji() {
        return countryCodeToEmoji(code);
    }

    public int getFlag(Context context) {
        return loadFlagByCode(context);
    }

    /**
     * This function is to load the flag image recourse from drawables folder based upon the country code
     */
    private int loadFlagByCode(Context context) {
        try {
            this.flag = context.getResources()
                    .getIdentifier("flag_" + this.code.toLowerCase(Locale.ENGLISH), "drawable",
                            context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            this.flag = -1;
        }

        return flag;
    }

    /**
     * This function is to return string of flag emoji based upon country code
     *
     * @param countryCode countryCode of the country
     * @return string of emoji for flag
     */
    private String countryCodeToEmoji(String countryCode) {
        int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }
    // endregion
}
