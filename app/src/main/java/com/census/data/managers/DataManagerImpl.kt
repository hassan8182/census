package com.census.data.managers

import com.census.data.local.prefs.PreferencesHelper
import com.census.utils.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */

@Singleton
class DataManagerImpl @Inject constructor(
//    private val apiService: ApiService,
    private val resourceProvider: ResourceProvider,
    private val preferencesHelper: PreferencesHelper
) : DataManager {
//    override fun getApiHelper(): ApiService {
//        return apiService
//    }

    override fun getPreferencesHelper(): PreferencesHelper {
        return preferencesHelper
    }

    override fun getResourceManager(): ResourceProvider {
        return resourceProvider
    }
}