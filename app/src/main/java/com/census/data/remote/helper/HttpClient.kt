package com.census.data.remote.helper

import com.census.data.local.prefs.PreferencesHelper
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */
object HttpClient {
    fun getHTTPClient(preferencesHelper: PreferencesHelper): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val request = chain.request()
            chain.proceed("preferencesHelper.getAccessToken()"?.let {
                request.newBuilder().addHeader(
                    "token",
                    "it"
                ).build()
            } ?: run {
                request
            })
        }

        /*if (BuildConfig.API_LOGGING && BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)
        }*/
        return httpClient.build()
    }
}