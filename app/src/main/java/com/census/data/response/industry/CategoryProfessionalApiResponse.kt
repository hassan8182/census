package com.census.data.response.industry

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryProfessionalApiResponse   {
    @Expose
    @SerializedName("expert_counts")
    var expert_counts: Int? = null
}