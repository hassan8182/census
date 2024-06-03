package com.census.data.request

import com.google.gson.annotations.SerializedName
import com.census.ui.base.api.BaseApiRequest

class SignUpRequest(
    @SerializedName("first_name")
    var first_name: String? = null,
    @SerializedName("last_name")
    var last_name: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("phone_number")
    var phone_number: String? = null,
    @SerializedName("country_code")
    var country_code: String? = null,
    @SerializedName("country_name")
    var country_name: String? = null,
    @SerializedName("password")
    var password: String? = null


) : BaseApiRequest()
