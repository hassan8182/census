package com.census.data.response.industry

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CategoryItem {
    @Expose(serialize = false, deserialize = false)
    var isSelected: Boolean = false

    @Expose
    @SerializedName("uid")
    var uid: String? = null

    @Expose
    @SerializedName("industory_title")
    var industory_title: String? = null

    @Expose
    @SerializedName("industory_icon")
    var industory_icon: String? = null
 }