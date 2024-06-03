package com.ilsa.countrypicker.utils.comparators;

import com.ilsa.countrypicker.models.Country;

import java.util.Comparator;

public class ISOCodeComparator implements Comparator<Country> {
        @Override
        public int compare(Country country, Country nextCountry) {
            return country.getCode().compareTo(nextCountry.getCode());
        }
    }