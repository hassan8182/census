package com.census.ui.dashboard


import com.census.data.local.db.DatabaseRepository
import com.census.ui.base.BaseViewModel
import com.census.ui.repository.AuthRepository
import com.census.utils.SingleLiveEvent

import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class DashboardActivityViewModel @Inject constructor() : BaseViewModel() {


    @Inject
    lateinit var repository: AuthRepository

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
    fun onClick(){
        event.value=DashboardActivity.ON_CLICK_CLICK
    }

    fun onBackPress() {
        event.value = DashboardActivity.ON_BACK_PRESS
    }








}