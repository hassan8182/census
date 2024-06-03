package com.census.ui.base.api

interface TaskListener<T> {
    fun onResponse(response: T)
    fun onError(apiError: ApiError?)
}