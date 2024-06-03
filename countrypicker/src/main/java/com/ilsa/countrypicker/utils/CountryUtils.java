package com.ilsa.countrypicker.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilsa.countrypicker.models.Country;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryUtils {

    private String loadJSONFromAsset(Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open("country_codes.json");

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }


    private void localizeCountryNames(ArrayList<Country> countries) {

        for (Country country : countries) {
            Locale locale = new Locale("", country.getCode());
            country.setName(locale.getDisplayCountry(Locale.getDefault()));
        }
    }

    public ArrayList<Country> getCountries(Context context) {
        String json;
        try {
            json = loadJSONFromAsset(context);
            Type listType = new TypeToken<List<Country>>() {
            }.getType();
            ArrayList<Country> countries = new Gson().fromJson(json, listType);
            localizeCountryNames(countries);

            return countries;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
