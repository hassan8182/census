package com.census.network

sealed class ApiState<out R> {

    object Loading : ApiState<Nothing>()
    object OnUnAuthorizeTokenFailed : ApiState<Nothing>()
    class Failure(val msg: String) : ApiState<Nothing>()
    class Success<out R>(val response: R) : ApiState<R>()

}
