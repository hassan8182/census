package com.census.ui.repository

import com.census.BuildConfig
import com.census.data.managers.DataManager
import com.census.data.request.LoginRequest
import com.census.data.request.SignUpRequest
import com.census.network.auth.AuthApi
import com.census.ui.base.api.BaseApiHelper
import com.census.ui.base.baseApiResultHandler
import com.census.utils.CommonUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(

) : BaseApiHelper() {

    @Inject
    lateinit var authApi: AuthApi

    @Inject
    lateinit var dataManager: DataManager


/*    fun callSignupApi(request: SignUpRequest) = baseApiResultHandler {
        //        "2022-12-07 12:13:44"
        val date = CommonUtils.getCurrentDateTimeForApiRequest()
//        val authorizationKey = CommonUtils.getAuthorizationToken(
//            ApiEndPoints.SIGNUP,
//            "post",
//            date,
//            request.phone_number!!
//        )
        authApi.signup(request, BuildConfig.API_KEY, date, "")
    }*/
    fun callLoginApi(request: LoginRequest) = baseApiResultHandler {
        authApi.login(request)
    }



}