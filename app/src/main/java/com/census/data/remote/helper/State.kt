package com.census.data.remote.helper

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */

sealed class State<out T> {

    data class Success<T>(val wrapperData: T) : State<T>()

    data class Error(val apiErrorHandler: ApiErrorHandler) : State<Nothing>()

    data class Loading(val wrapperData: Nothing? = null) : State<Nothing>()

    companion object {

        /**
         * Returns [State.Success] instance.
         * @param data Data to emit with status.
         */
        fun <T> success(data: T) = Success(data)

        /**
         * Returns [State.Error] instance.
         * @param message Description of failure.
         */
        fun <T> error(apiErrorHandler: ApiErrorHandler) =
            Error(apiErrorHandler)
    }
}