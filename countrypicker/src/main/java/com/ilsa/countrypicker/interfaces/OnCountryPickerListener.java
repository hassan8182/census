package com.ilsa.countrypicker.interfaces;

import com.ilsa.countrypicker.models.Country;

public interface OnCountryPickerListener {
    void onSelectCountry(Country country);

    void onDismiss();
}
