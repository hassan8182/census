package com.census.data.response.census

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @Expose
    @SerializedName("user")
    var user: User? = null
    @Expose
    @SerializedName("message")
    var message: String? = null
    @Expose
    @SerializedName("remember_token")
    var rememberToken: String? = null
}