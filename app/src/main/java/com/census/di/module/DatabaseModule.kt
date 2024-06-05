package com.census.di.module

import com.census.data.local.db.dao.SyncDao

import android.content.Context
import androidx.room.Room
import com.census.data.local.db.AppDatabase

import com.census.data.local.db.helper.AppDbHelper
import com.census.data.local.db.helper.DbHelper
import com.census.di.qualifiers.DatabaseInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            "${context.getExternalFilesDir("Ret")?.absolutePath}/RetDatabase"
        )
//            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
        // .createFromAsset("temp.db")
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return "Census"
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }
    @Provides
    fun provideSyncDao(database: AppDatabase): SyncDao = database.syncDao()





}