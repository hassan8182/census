package com.census.ui.blocks


import com.census.data.local.db.DatabaseRepository
import com.census.ui.base.BaseViewModel
import com.census.ui.repository.AuthRepository
import com.census.utils.SingleLiveEvent

import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class BlockSelectionActivityViewModel @Inject constructor() : BaseViewModel() {


    @Inject
    lateinit var repository: AuthRepository

    @Inject
    lateinit var userRepository: DatabaseRepository







    val event = SingleLiveEvent<@BlockSelectionActivityClickEvents Int>()

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
        event.value=BlockSelectionActivity.ON_CLICK_CLICK
    }

    fun onBackPress() {
        event.value = BlockSelectionActivity.ON_BACK_PRESS
    }
    fun onDotsClick(){
        event.value=BlockSelectionActivity.ON_CLICK_CLICK
    }








}