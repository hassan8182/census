package com.census.data.local.db.tables.syncdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_data")
data class SyncData (
    @PrimaryKey(autoGenerate = false) val id:Int?=null,
    val provinceName: String? = null,
    val districtCode: Int? = null,
    val districtName: String? = null,
    val tehsilCode: Int? = null,
    val tehsilName: String? = null,
    val cvCode: Int? = null,
    val urbanity: String? = null,
    val cvName: String? = null,
    val arTehsil: String? = null,
    val blockId: Int? = null,
    val uniqueId: String? = null,
    val univ2012: String? = null,
    val univ2022: String? = null,
    val fieldExecutive: String? = null,
    val execId: String? = null,
    val fieldSup: String? = null,
    val supId: String? = null,
    val iqcSup: String? = null,
    val iqcsupId: String? = null,
    val fieldAuditor: String? = null,
    val auditorId: String? = null,
    val iqcAuditor: String? = null,
    val iqcauditorId: String? = null,
    val assigned: String? = null,
    val flagged: String? = null,
    val resolved: String? = null,
    val csvFileName: String? = null,
    val arGeoFileName: String? = null,
    val fkMap: Int? = null,
    val status: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val fileName: String? = null,
    val blockIdDuplicate: Int? = null,
    val json: String? = null,
    val active: Int? = null,
    val city: String? = null
)