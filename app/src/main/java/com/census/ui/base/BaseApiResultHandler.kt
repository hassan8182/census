package com.census.ui.base

import com.census.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import org.json.JSONObject


fun <T> baseApiResultHandler(call: suspend () -> Response<T>): Flow<ApiState<T?>> = flow {

    emit(ApiState.Loading)

    try {

        val c = call()

        if (c.isSuccessful) {
//            if (c.body() is BaseApiResponse
//                && (c.body() as BaseApiResponse).status != null
//                && (c.body() as BaseApiResponse).status == 401
//            ) {
//                val message = (c.body() as BaseApiResponse).message
//                if (message.isNullOrEmpty())
//                    emit(ApiState.OnUnAuthorizeTokenFailed)
//                else
//                    emit(ApiState.Failure(message))
//            } else
//                emit(ApiState.Success(c.body()))
                emit(ApiState.Success(c.body()))
        } else {
            c.errorBody()?.let { error ->
                error.close()
                try {
                    val jObjError = JSONObject(error.string())
                    emit(ApiState.Failure(jObjError.getJSONObject("error").getString("message")))
                } catch (e: java.lang.Exception) {
                    emit(ApiState.Failure(error.toString()))
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        emit(ApiState.Failure(e.message.toString()))
    }

}.flowOn(Dispatchers.IO)