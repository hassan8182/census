package com.census.data.response.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthApiResponse  {
    @Expose
    @SerializedName("user")
    var user: User? = null
}