package com.census.ui.dashboard


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.census.data.local.db.DatabaseRepository
import com.census.data.local.db.tables.syncdata.SyncData


import com.census.data.response.census.Data
import com.census.data.response.census.SyncDataResponse
import com.census.network.ApiState
import com.census.ui.base.BaseActivity
import com.census.ui.base.BaseViewModel
import com.census.ui.repository.HomeRepository
import com.census.utils.SingleLiveEvent

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class DashboardActivityViewModel @Inject constructor() : BaseViewModel() {


    @Inject
    lateinit var repository: HomeRepository

    @Inject
    lateinit var databaseRepository: DatabaseRepository


    val event = SingleLiveEvent<@DashboardActivityClickEvents Int>()

    fun onSyncClick() {
        event.value = DashboardActivity.ON_SYNC_CLICK
    }

    fun onBackPress() {
        event.value = DashboardActivity.ON_BACK_PRESS
    }

    fun getSyncData(

        url: String,
        onSuccess: (message: String?, synData: Data?) -> Unit,
        onFailure: (message: String?) -> Unit
    ) {
        viewModelScope.launch {
            repository.getSyncData(url).collect { it ->
                when (it) {
                    is ApiState.Loading -> {
                        baseEvent.value = BaseActivity.SHOW_LOADER
                    }

                    is ApiState.OnUnAuthorizeTokenFailed -> {
                        baseEvent.value = BaseActivity.HIDE_LOADER
                        baseEvent.value = BaseActivity.EXPIRED_USER_TOKEN
                    }

                    is ApiState.Success -> {
                        baseEvent.value = BaseActivity.HIDE_LOADER
                        if (it.response != null) {
                            onSuccess.invoke("success", it.response.syncData)

                            it.response.syncData?.let {
                                databaseRepository.insertSyncData(
                                    SyncData(
                                        id = it.id,
                                        districtCode = it.districtCode,
                                        districtName = it.districtName,
                                        tehsilCode = it.tehsilCode,
                                        tehsilName = it.tehsilName,
                                        cvCode = it.cvCode,
                                        urbanity = it.urbanity,
                                        cvName = it.cvName,
                                        arTehsil = it.arTehsil,
                                        blockId = it.blockId,
                                        uniqueId = it.uniqueId,
                                        univ2012 = it.univ2012,
                                        univ2022 = it.univ2022,
                                        fieldExecutive = it.fieldExecutive,
                                        execId = it.execId,
                                        fieldSup = it.fieldSup,
                                        supId = it.supId,
                                        iqcSup = it.iqcSup,
                                        iqcsupId = it.iqcsupId,
                                        fieldAuditor = it.fieldAuditor,
                                        auditorId = it.auditorId,
                                        iqcAuditor = it.iqcAuditor,
                                        iqcauditorId = it.iqcauditorId,
                                        assigned = it.assigned,
                                        flagged = it.flagged,
                                        resolved = it.resolved,
                                        csvFileName = it.csvFileName,
                                        arGeoFileName = it.arGeoFileName,
                                        fkMap = it.fkMap,
                                        status = it.status,
                                        createdAt = it.createdAt,
                                        updatedAt = it.updatedAt,
                                        fileName = it.fileName,
                                        blockIdDuplicate = it.blockIdAlt,
                                        json = it.json,
                                        active = it.active,
                                        city = it.city
                                    )
                                )
                            }

                        }


                    }

                    is ApiState.Failure -> {
                        baseEvent.value = BaseActivity.HIDE_LOADER
                        Log.d("message", it.msg.toString())
                        onFailure.invoke(it.msg)
                    }
                }
            }
        }
    }


}