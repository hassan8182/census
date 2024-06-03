package com.census.ui.base.api

import com.google.gson.annotations.SerializedName
import com.census.utils.Constants
import java.util.*

open class BaseApiRequestNoToken(

    @SerializedName("lang")
    var lang: String = Constants.ENGLISH_LANGUAGE,
    @SerializedName("time_zone")
    var time_zone: String = TimeZone.getDefault().id,
    @SerializedName("device_type")
    var device_type: String = Constants.ANDROID,
    @SerializedName("role")
    var role: String? = null

)