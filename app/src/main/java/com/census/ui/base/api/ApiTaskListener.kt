package com.census.ui.base.api

import io.reactivex.rxjava3.core.Observable

interface ApiTaskListener<T> : TaskListener<T> {
    val request: Observable<T>?
}