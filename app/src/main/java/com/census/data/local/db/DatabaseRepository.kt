package com.census.data.local.db


import com.census.data.local.db.dao.SyncDao

import com.census.data.local.db.tables.syncdata.SyncData
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val syncDao: SyncDao,

){
    suspend fun insertSyncData(syncData: SyncData?)=syncDao.insertSyncData(syncData)


}