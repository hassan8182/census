package com.census.utils

interface PermissionListener<T> {
    fun onResponse(response: T)
    fun onError()
    fun onDoNotAskAgain()
}