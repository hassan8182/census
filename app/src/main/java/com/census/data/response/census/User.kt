package com.census.data.response.census

import com.census.data.response.auth.ExpertPriceDuration
import com.census.data.response.auth.Keys
import com.census.data.response.auth.ProfessionalSettingsItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class User {


    @Expose
    @SerializedName("id")
    var id: Int? = null

    @Expose
    @SerializedName("fullname")
    var fullname: String? = null

    @Expose
    @SerializedName("username")
    var username: String? = null

    @Expose
    @SerializedName("username_verified_at")
    var usernameVerifiedAt: String? = null

    @Expose
    @SerializedName("user_type")
    var userType: String? = null

    @Expose
    @SerializedName("assign_exe")
    var assignExe: String? = null

    @Expose
    @SerializedName("assign_sup")
    var assignSup: String? = null

    @Expose
    @SerializedName("status")
    var status: Int? = null

    @Expose
    @SerializedName("created_at")
    var createdAt: String? = null

    @Expose
    @SerializedName("updated_at")
    var updatedAt: String? = null

}