package com.census.network.home

import com.census.data.request.*
import com.census.data.response.ResponseData
import com.census.data.response.census.LoginResponse
import com.census.data.response.census.SyncDataResponse

import com.census.data.response.industry.CategoryApiResponse
import com.census.network.ApiEndPoints
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface HomeApi {


    @GET(ApiEndPoints.GET_CATEGORIES)
    suspend fun getCategory(
        @Header("apikey") api_key: String,
        @Header("Authorization") token: String
    ): Response<ResponseData<CategoryApiResponse>>
    @GET
    suspend fun getSyncData(
        @Url url:String
    ): Response<SyncDataResponse>


}