package com.census.network

interface ApiEndPoints {

    companion object {

        const val GOOGLE_APIS_BASE_URL = "https://maps.googleapis.com/maps/api/place"
        const val GOOGLE_PLACES_DETAILS = "$GOOGLE_APIS_BASE_URL/details/json"
        const val GOOGLE_NEARBY_PLACES: String = "$GOOGLE_APIS_BASE_URL/nearbysearch/json"
        const val GOOGLE_AUTO_COMPLETE: String = "$GOOGLE_APIS_BASE_URL/autocomplete/json"

        const val LOGIN = "login"
        const val SIGNUP = "signup_secure"
        const val GET_CATEGORIES = "getcategory"

    }
}