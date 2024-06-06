package com.census

import android.app.Application
import android.content.Context
import com.census.data.local.db.helper.DbHelper
import com.census.data.managers.DataManager
import com.census.network.home.HomeApi
import com.census.ui.repository.HomeRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CensusApp : Application() {

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var dbManager: DbHelper

    @Inject
    lateinit var homeApi: HomeApi

    @Inject
    lateinit var homeRepository: HomeRepository



    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }

    companion object {

        const val TAG = "Census"

        lateinit var context: Context

    }
}