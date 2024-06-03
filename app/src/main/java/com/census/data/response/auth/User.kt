package com.census.data.response.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class User {
    @Expose
    @SerializedName("uid")
    var uid: String? = null

    @Expose
    @SerializedName("first_name")
    var first_name: String? = null

    @Expose
    @SerializedName("last_name")
    var last_name: String? = null

    @Expose
    @SerializedName("email")
    var email: String? = null

    @Expose
    @SerializedName("country_code")
    var country_code: String? = null

    @Expose
    @SerializedName("profile_picture")
    var profile_picture: String? = null


    @Expose
    @SerializedName("country_name")
    var country_name: String? = null

    @Expose
    @SerializedName("phone_number")
    var phone_number: String? = null

    @Expose
    @SerializedName("role")
    var role: String? = null


    @Expose
    @SerializedName("is_verified")
    var is_verified: Boolean? = null

    @Expose
    @SerializedName("password_created")
    var password_created: Boolean? = null

    @Expose
    @SerializedName("social_id")
    var social_id: String? = null

    @Expose
    @SerializedName("environment")
    var environment: String? = null

    @Expose
    @SerializedName("token")
    var token: String? = null

    @Expose
    @SerializedName("expires_in")
    var expires_in: String? = null

    @Expose
    @SerializedName("token_type")
    var token_type: String? = null

    @Expose
    @SerializedName("stripe_customer_id")
    var stripe_customer_id: String? = null

    @Expose
    @SerializedName("expert_subcription_uid")
    var expert_subcription_uid: String? = null

    @Expose
    @SerializedName("onboard")
    var onboard: Onboard? = null

    @Expose
    @SerializedName("expert_settings")
    var expert_settings: ProfessionalSettingsItem? = null


    @Expose
    @SerializedName("session_price")
    var session_price: ArrayList<ExpertPriceDuration>? = null

    @Expose
    @SerializedName("category")
    var category: String? = null

    @Expose
    @SerializedName("status")
    var status: String? = null

    @Expose
    @SerializedName("version_number")
    var version_number: String? = null

    class Onboard {
        @Expose
        @SerializedName("customer_interest_added")
        var customer_interest_added: Boolean? = null

        @Expose
        @SerializedName("profession_added")
        var profession_added: Boolean? = null

        @Expose
        @SerializedName("availabilty_added")
        var availabilty_added: Boolean? = null

        @Expose
        @SerializedName("duration_prices_added")
        var duration_prices_added: Boolean? = null

        @Expose
        @SerializedName("duration_description_added")
        var duration_description_added: Boolean? = null

        @Expose
        @SerializedName("profile_information_added")
        var profile_information_added: Boolean? = null
    }

    @Expose
    @SerializedName("about_me")
    var about_me: String? = null

    @Expose
    @SerializedName("career_highlight")
    var career_highlight:String? = null

    @Expose
    @SerializedName("connect_reason")
    var connect_reason: ArrayList<String>? = null


    @Expose
    @SerializedName("notification_count")
    var notification_count: Int = 0

    @Expose
    @SerializedName("keys")
    var keys: Keys? = null
}