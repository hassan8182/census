package com.census.ui.base

import com.census.R
import com.census.data.managers.DataManager
import com.census.data.remote.helper.ApiErrorHandler
import com.census.data.remote.helper.State
import com.census.utils.NetworkUtils
import com.census.utils.StatusCodes
import retrofit2.Response

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */
class BaseRepository(private val dataManager: DataManager) {

    suspend fun <T : Any> makeApiCall(
        apiCall: suspend () -> Response<T>,
    ): State<T> {
        return verifySessionAndMakeApiCall(apiCall, false)
    }


    suspend fun <T : Any> verifySessionAndMakeApiCall(
        call: suspend () -> Response<T>,
        verifySession: Boolean = true,
    ): State<T> {
        if (NetworkUtils.isNetworkConnected(
                dataManager.getResourceManager().context
            )
        ) {
            return State.Error(
                ApiErrorHandler(
                    StatusCodes.INTERNET_NOT_AVAILABLE, dataManager.getResourceManager().getString(
                        R.string.internet_error
                    )
                )
            )
        }
        return apiOutput { call() }
        /*
        return if (verifySession*//* && isTokenExpired()*//*) {
            val sessionResult = apiOutput {
                dataManager.getApiHelper()
                    .refreshToken(dataManager.getPreferencesHelper().getRefreshToken() ?: "")
            }
            when (sessionResult) {
                is State.Success -> {
                    if (sessionResult.wrapperData.success) {
                        sessionResult.wrapperData.data?.accessToken?.let { dataManager.getPreferencesHelper().setAccessToken(it) }
                        apiOutput(call)
                    } else {
                        failedToUpdateRefreshToken()
                    }
                }
                is State.Error ->
                    sessionResult
            }
        } else {
            apiOutput(call)
        }*/
    }

    private suspend fun <T : Any> apiOutput(
        call: suspend () -> Response<T>,
    ): State<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            State.Success(response.body()!!)
        else {
            State.Error(ApiErrorHandler(response.code(), response.message()))
        }
    }
}