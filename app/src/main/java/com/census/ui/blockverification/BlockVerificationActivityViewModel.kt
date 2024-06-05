package com.census.ui.blockverification


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
        event.value=BlockVerificationActivity.ON_CLICK_CLICK
    }

    fun onBackPress() {
        event.value = BlockVerificationActivity.ON_BACK_PRESS
    }








}