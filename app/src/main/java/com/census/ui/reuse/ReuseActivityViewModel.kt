package com.census.ui.reuse


import com.census.ui.base.BaseViewModel
import com.census.ui.repository.AuthRepository
import com.census.utils.SingleLiveEvent

import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class ReuseActivityViewModel @Inject constructor() : BaseViewModel() {


    @Inject
    lateinit var repository: AuthRepository


    val event = SingleLiveEvent<@ReuseActivityClickEvents Int>()


    fun onClick(){
        event.value=ReuseActivity.ON_CLICK_CLICK
    }

    fun onBackPress() {
        event.value = ReuseActivity.ON_BACK_PRESS
    }








}