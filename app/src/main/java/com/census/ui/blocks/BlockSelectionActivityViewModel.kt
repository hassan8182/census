package com.census.ui.blocks



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.census.data.local.db.DatabaseRepository
import com.census.data.local.db.tables.syncdata.SyncData
import com.census.ui.base.BaseViewModel
import com.census.ui.repository.AuthRepository
import com.census.utils.SingleLiveEvent

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class BlockSelectionActivityViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) : BaseViewModel() {


    val event = SingleLiveEvent<@BlockSelectionActivityClickEvents Int>()
    val syncData: LiveData<List<SyncData>> = databaseRepository.readSyncData()


    fun onClick(){
        event.value=BlockSelectionActivity.ON_CLICK_CLICK
    }

    fun onBackPress() {
        event.value = BlockSelectionActivity.ON_BACK_PRESS
    }
    fun onDotsClick(){
        event.value=BlockSelectionActivity.ON_CLICK_CLICK
    }








}