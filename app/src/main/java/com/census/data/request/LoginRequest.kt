package com.census.data.request

import com.google.gson.annotations.SerializedName
import com.census.ui.base.api.BaseApiRequest

class LoginRequest(
    @SerializedName("username")
    var userName: String? = null,
    @SerializedName("password")
    var password: String? = null


) : BaseApiRequest()
