package com.census.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.census.ui.base.api.BaseApiResponse

data class ResponseData<T>(
    @SerializedName("data")
    @Expose
    var data: T?) : BaseApiResponse()
