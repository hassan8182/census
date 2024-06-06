package com.census.ui.blockverification


import androidx.lifecycle.MutableLiveData
import com.census.ui.base.BaseViewModel
import com.census.ui.repository.AuthRepository
import com.census.utils.SingleLiveEvent

import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class BlockVerificationActivityViewModel @Inject constructor() : BaseViewModel() {


    @Inject
    lateinit var repository: AuthRepository


    val event = SingleLiveEvent<@BlockVerificationActivityClickEvents Int>()
    val isErrorTextShowing = MutableLiveData(false)


    fun onCaptureLocationClick(){
        event.value=BlockVerificationActivity.ON_CAPTURE_LOCATION_CLICK
    }

    fun onBackPress() {
        event.value = BlockVerificationActivity.ON_BACK_PRESS
    }








}