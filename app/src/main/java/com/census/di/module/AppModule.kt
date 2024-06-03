package com.census.di.module

import com.census.BuildConfig

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.census.data.local.db.AppDatabase
import com.census.data.local.db.helper.AppDbHelper
import com.census.data.local.db.helper.DbHelper
import com.census.data.local.prefs.PreferencesHelper
import com.census.data.local.prefs.PreferencesHelperImpl
import com.census.data.managers.DataManager
import com.census.data.managers.DataManagerImpl
import com.census.di.qualifiers.DatabaseInfo
import com.census.di.rxjava.AppSchedulerProvider
import com.census.di.rxjava.SchedulerProvider
import com.census.utils.Constants
import com.census.utils.LimitConstants
import com.census.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofitInstance(dataManager: DataManager): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG)
            client.addInterceptor(loggingInterceptor)
        client.interceptors().add(Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            request = request
                .newBuilder()
                .build()
            chain.proceed(request)
        })


        client.connectTimeout(LimitConstants.RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(LimitConstants.RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(LimitConstants.RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(LimitConstants.RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)


        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }


    @Provides
    fun providePreferenceName(): String {
        return Constants.PREF_NAME
    }

    @Singleton
    @Provides
    fun providePreferenceHelper(preferencesHelperImpl: PreferencesHelperImpl): PreferencesHelper {
        return preferencesHelperImpl
    }

    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProvider(context.applicationContext)
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Singleton
    @Provides
    fun provideDataManager(dataManagerImpl: DataManagerImpl): DataManager {
        return dataManagerImpl
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }


    @Provides
    @Singleton
    fun provideAppDatabase(
        @DatabaseInfo dbName: String?,
        @ApplicationContext context: Context?
    ): AppDatabase {
        return Room.databaseBuilder(context!!, AppDatabase::class.java, dbName!!)
//            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return "racetofreedomDb"
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

}