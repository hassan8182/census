package com.census.ui.base.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseApiResponse {
    @Expose
    @SerializedName("success")
    var isSuccess = false

    @Expose
    @SerializedName("message")
    var message: String? = null

    @Expose
    @SerializedName("status")
    var status: Int? = null

}