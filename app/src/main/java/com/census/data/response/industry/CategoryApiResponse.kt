package com.census.data.response.industry

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryApiResponse   {
    @Expose
    @SerializedName("categories")
    var categories: ArrayList<CategoryItem>? = null
}