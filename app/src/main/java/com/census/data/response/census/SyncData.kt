package com.census.data.response.census

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SyncData {
    @Expose
    @SerializedName("success")
    var fullname: Boolean? = null
    @Expose
    @SerializedName("data")
    var syncData: Data? = null
}