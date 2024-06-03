package com.census.ui.repository

import com.census.BuildConfig
import com.census.data.managers.DataManager
import com.census.network.home.HomeApi
import com.census.ui.base.api.BaseApiHelper
import com.census.ui.base.baseApiResultHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(

) : BaseApiHelper() {

    @Inject
    lateinit var homeApi: HomeApi

    @Inject
    lateinit var dataManager: DataManager


    fun getCategories() = baseApiResultHandler {
        homeApi.getCategory(
            BuildConfig.API_KEY, ""
        )
    }
}