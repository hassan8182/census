package com.census.ui.base.api

import com.census.CensusApp
import com.census.di.rxjava.SchedulerProvider
import com.census.utils.NetworkUtils
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

open class BaseApiHelper @Inject constructor() {

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private val apiTaskListeners: Set<ApiTaskListener<*>>

    fun <T> executeApiCall(taskListener: ApiTaskListener<T>?) {
        this.executeApiCall(false, taskListener)
    }

    fun <T> executeApiCall(ignoreNetworkCheck: Boolean, taskListener: ApiTaskListener<T>?) {

        var request: Observable<T>? = null

        val shouldReturn =
            !ignoreNetworkCheck && isNetworkNotAvailable(taskListener) || taskListener == null
                    || (taskListener.request.also { request = it }) == null

        if (shouldReturn) {
            return
        }

        if (request != null) {

            compositeDisposable.add(
                request!!
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ response ->
                        taskListener?.onResponse(response)
                    }) { throwable ->
                        taskListener?.onError(ApiError(500, "", throwable.message))
                    })

        }
    }

    private fun <T> isNetworkNotAvailable(taskListener: TaskListener<T>?): Boolean {
        var isConnected = true
        if (!NetworkUtils.isNetworkConnected(CensusApp.context)) {
            taskListener?.onError(networkError)
            isConnected = false
        }
        return !isConnected
    }

    private val networkError: ApiError
        get() = ApiError(400, "400", "internet error")


    init {
        apiTaskListeners = HashSet<ApiTaskListener<*>>()
    }
}