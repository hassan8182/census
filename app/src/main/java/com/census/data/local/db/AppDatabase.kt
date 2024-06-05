package com.census.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.census.data.local.db.dao.MediaUploadDao
import com.census.data.local.db.dao.PostUploadDao
import com.census.data.local.db.dao.SyncDao
import com.census.data.local.db.tables.MediaUpload
import com.census.data.local.db.tables.PostUpload
import com.census.data.local.db.tables.syncdata.SyncData


@Database(
    entities = [PostUpload::class, MediaUpload::class, SyncData::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(
)


abstract class AppDatabase : RoomDatabase() {
    abstract fun postUploadDao(): PostUploadDao?
    abstract fun mediaUploadDao(): MediaUploadDao?
    abstract fun syncDao():SyncDao

}