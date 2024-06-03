package com.census.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.census.data.local.db.doa.MediaUploadDao
import com.census.data.local.db.doa.PostUploadDao
import com.census.data.local.db.tables.MediaUpload
import com.census.data.local.db.tables.PostUpload


@Database(
    entities = [PostUpload::class, MediaUpload::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(
)


abstract class AppDatabase : RoomDatabase() {
    abstract fun postUploadDao(): PostUploadDao?
    abstract fun mediaUploadDao(): MediaUploadDao?

}