package com.census.network.auth

import com.census.BuildConfig
import com.census.data.request.*
import com.census.data.response.ResponseData
import com.census.data.response.auth.AuthApiResponse
import com.census.data.response.census.LoginResponse
import com.census.network.ApiEndPoints
import com.census.ui.base.api.BaseApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

   /* @POST(ApiEndPoints.LOGIN)
    suspend fun login(
        @Body request: LoginRequest,
        @Header("apikey") api_key: String
    ): Response<ResponseData<AuthApiResponse>>

    @POST(ApiEndPoints.SIGNUP)
    suspend fun signup(
        @Body request: SignUpRequest,
        @Header("apikey") api_key: String,
        @Header("b-datetime") bts: String,
        @Header("b-authorization") authorizationKey: String,
        @Header("appVersion") appVersion: String = BuildConfig.VERSION_NAME
    ): Response<BaseApiResponse>*/

    @POST(ApiEndPoints.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>


}