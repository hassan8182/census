package com.ilsa.countrypicker.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ilsa.countrypicker.CountryPickerDialog;
import com.ilsa.countrypicker.R;
import com.ilsa.countrypicker.interfaces.OnCountryPickerListener;
import com.ilsa.countrypicker.models.Country;
import com.ilsa.countrypicker.utils.comparators.ISOCodeComparator;
import com.ilsa.countrypicker.utils.comparators.NameComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CountryPicker implements CountryPickerDialog.CountryPickerDialogInteractionListener {

    // region Variables
    public static final int SORT_BY_NONE = 0;
    // endregion
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_ISO = 2;
    public static final int SORT_BY_DIAL_CODE = 3;
    private static final String COUNTRY_TAG = "COUNTRY_PICKER";

    private Context context;
    private int sortBy = SORT_BY_NONE;
    private OnCountryPickerListener onCountryPickerListener;
    private boolean canSearch = true;

    private ArrayList<Country> countries;

    private CountryUtils mCountryUtils;

    // region Constructors
    private CountryPicker() {
    }

    CountryPicker(Builder builder) {
        mCountryUtils = new CountryUtils();
        sortBy = builder.sortBy;
        onCountryPickerListener = builder.onCountryPickerListener;
        context = builder.context;
        canSearch = builder.canSearch;
        //countries = new ArrayList<>(Arrays.asList(mCountryUtils.COUNTRIES));
        countries = mCountryUtils.getCountries(context);
        sortCountries(countries);
    }
    // endregion

    // region Listeners
    @Override
    public void sortCountries(@NonNull List<Country> countries) {
        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country country1, Country country2) {
                switch (sortBy) {
                    case SORT_BY_ISO:
                        return country1.getCode().trim().compareToIgnoreCase(country2.getCode().trim());
                    case SORT_BY_DIAL_CODE:
                        return country1.getDialCode().trim().compareToIgnoreCase(country2.getDialCode().trim());
                    case SORT_BY_NAME:
                    default:
                        return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());

                }
            }
        });
    }

    @Override
    public ArrayList<Country> getAllCountries() {
        return countries;
    }


    @Override
    public boolean canSearch() {
        return canSearch;
    }

    // endregion

    // region Utility Methods
    public void showDialog(@NonNull FragmentManager supportFragmentManager) {
        if (countries == null || countries.isEmpty()) {
            throw new IllegalArgumentException(context.getString(R.string.error_no_countries_found));
        } else {
            CountryPickerDialog countryPickerDialog = CountryPickerDialog.newInstance();
            countryPickerDialog.setCountryPickerListener(onCountryPickerListener);
            countryPickerDialog.setDialogInteractionListener(this);
            countryPickerDialog.show(supportFragmentManager, COUNTRY_TAG);
        }
    }

    public void setCountries(@NonNull List<Country> countries) {
        this.countries.clear();
        this.countries.addAll(countries);
        sortCountries(this.countries);
    }

    public Country getCountryFromSIM(@NonNull Context context) {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null && telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
            return getCountryByISO(telephonyManager.getSimCountryIso());
        }
        return null;
    }

    public Country getCountryByLocale(@NonNull Locale locale) {
        //  String countryIsoCode = locale.getISO3Country().substring(0, 2).toLowerCase();
        String countryIsoCode = locale.getCountry();
        Log.d("usm_locale", "country= " + locale.getCountry() + " ,iso3= " + locale.getISO3Country());
        return getCountryByISO(countryIsoCode);
    }

    public Country getCountryByName(@NonNull String countryName) {
        countryName = countryName.toUpperCase();
        Country country = new Country();
        country.setName(countryName);
        return searchCountry(country, new NameComparator());
    }

    public Country getCountryByISO(@NonNull String countryIsoCode) {
        countryIsoCode = countryIsoCode.toUpperCase();
        Country country = new Country();
       // Log.d("usm_country_iso", "isoCode= " + countryIsoCode);

        country.setCode(countryIsoCode);
        return searchCountry(country, new ISOCodeComparator());
    }

    private Country searchCountry(Country country, Comparator comparator) {
        int i = countries != null ? Arrays.binarySearch(countries.toArray(), country, comparator) : -1;

        Country matchedCountry = i < 0 ? null : countries.get(i);

        if (matchedCountry == null) {
            matchedCountry = findCountryByIsoCode(country.getCode());
        }

        return matchedCountry;
    }


    private Country findCountryByIsoCode(String isoCode) {
        Country result = null;
        for (Country country : countries) {
            if (country.getCode().equalsIgnoreCase(isoCode)) {
                result = country;
                break;
            }
        }
        return result;
    }


    // endregion

    // region Builder
    public static class Builder {
        private Context context;
        private int sortBy = SORT_BY_NONE;
        private boolean canSearch = true;
        private OnCountryPickerListener onCountryPickerListener;

        public Builder with(@NonNull Context context) {
            this.context = context;
            return this;
        }

        public Builder sortBy(int sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public Builder listener(@NonNull OnCountryPickerListener onCountryPickerListener) {
            this.onCountryPickerListener = onCountryPickerListener;
            return this;
        }

        public Builder canSearch(boolean canSearch) {
            this.canSearch = canSearch;
            return this;
        }

        public CountryPicker build() {
            if (onCountryPickerListener == null) {
                throw new IllegalArgumentException(
                        context.getString(R.string.error_listener_not_set));
            }
            return new CountryPicker(this);
        }
    }

    // endregion
}
