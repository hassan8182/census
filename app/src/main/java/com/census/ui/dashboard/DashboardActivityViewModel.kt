package com.census.ui.dashboard


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.census.data.local.db.DatabaseRepository
import com.census.data.request.LoginRequest
import com.census.data.response.census.Data
import com.census.data.response.census.User
import com.census.network.ApiState
import com.census.ui.base.BaseActivity
import com.census.ui.base.BaseViewModel
import com.census.ui.repository.AuthRepository
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
    lateinit var userRepository: DatabaseRepository







    val event = SingleLiveEvent<@DashboardActivityClickEvents Int>()

/*    fun test(user: Users){
        viewModelScope.launch {
            userRepository.insert(user)
        }

    }
    fun addQuestion(question:Question){
        viewModelScope.launch {
            userRepository.insertQuestion(question)
        }
    }
    fun getQuestions(){
        viewModelScope.launch {
            var question:List<Question>
            question=userRepository.getQuestions()
            Log.d("show_question","${question}")
        }
        }*/
    fun onSyncClick(){
        event.value=DashboardActivity.ON_SYNC_CLICK
    }

    fun onBackPress() {
        event.value = DashboardActivity.ON_BACK_PRESS
    }

    fun getSyncData(

        url:String,
        onSuccess: (message: String?,synData: Data?) -> Unit,
        onFailure: (message: String?) -> Unit
    ) {
        viewModelScope.launch {
            repository.getSyncData(url).collect {
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
                        if (it.response!=null){
                            onSuccess.invoke("success",it.response.syncData)
                        }

//                        if (it.response != null && it.response.message!=null) {
//                            Log.d("user","${it.response.user?.name}")
//                            onSuccess.invoke(it.response.message,it.response.token,it.response.user)
//                        } else {
//                            Log.d("error","${it.response?.errors}")
//
//                            onFailure.invoke(
//                                it.response?.errors?.error?.get(0).toString()
//                            )
//                        }
                    }

                    is ApiState.Failure -> {
                        baseEvent.value = BaseActivity.HIDE_LOADER
                        Log.d("message",it.msg.toString())
                        onFailure.invoke(it.msg)
                    }
                }
            }
        }
    }








}