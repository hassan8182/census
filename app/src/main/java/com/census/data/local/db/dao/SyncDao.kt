package com.census.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.census.data.local.db.tables.syncdata.SyncData
import com.census.data.response.census.SyncDataResponse
@Dao
interface SyncDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncData(syncData: SyncData?)

    @Query("Select * FROM sync_data ")
    fun readSyncData():LiveData<List<SyncData>>
    @Query("UPDATE sync_data SET active = :active WHERE id = :id")
    suspend fun updateSyncDataActive(id: Int, active: Int)

    @Query("SELECT * FROM sync_data WHERE active = 1")
    suspend fun readActiveData(): SyncData


}