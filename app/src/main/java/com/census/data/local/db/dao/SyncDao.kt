package com.census.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.census.data.local.db.tables.syncdata.SyncData
import com.census.data.response.census.SyncDataResponse
@Dao
interface SyncDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncData(syncData: SyncData?)
}