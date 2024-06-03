package com.census.data.response.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProfessionalSettingsItem {
    @Expose
    @SerializedName("minimum_booking_notice")
    var minimum_booking_notice: Int? = null
    @Expose
    @SerializedName("buffer")
    var buffer: Int? = null
    @Expose
    @SerializedName("slot_increment")
    var slot_increment: Int? = null
}