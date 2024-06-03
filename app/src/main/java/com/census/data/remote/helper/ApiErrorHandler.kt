package com.census.data.remote.helper

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */
data class ApiErrorHandler(
    var statusCode: Int? = null,
    var errorMessage: String? = null
)