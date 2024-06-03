package com.census.data.response.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ExpertPriceDuration(
    @Expose
    @SerializedName("id")
    var id: String? = null,
    @Expose
    @SerializedName("uid")
    var uid: String? = null,
    @Expose
    @SerializedName("expert_id")
    var expert_id: String? = null,
    @Expose
    @SerializedName("duration")
    var duration: Int? = null,
    @Expose
    @SerializedName("price")
    var price: Double? = null,
    @Expose
    @SerializedName("price_description")
    var price_description: ArrayList<String>? = ArrayList(),
    @Expose
    @SerializedName("created_at")
    var created_at: String? = null,
    @Expose
    @SerializedName("updated_at")
    var updated_at: String? = null,
    @Expose
    @SerializedName("is_archive")
    var is_archive: Int? = null
)