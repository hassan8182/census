package com.census.data.managers

import com.census.data.local.prefs.PreferencesHelper
import com.census.utils.ResourceProvider

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */
interface DataManager {
    //        fun getDbHelper(): DbHelper
//    fun getApiHelper(): ApiService
    fun getPreferencesHelper(): PreferencesHelper
    fun getResourceManager(): ResourceProvider
}