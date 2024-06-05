package com.census.ui.blocks.adapter

import android.util.Log
import androidx.lifecycle.MutableLiveData

class BlockSelectionItemViewModel constructor(
    val item: String, position: Int,
    val onItemClick: () -> Unit
) {

    val isArrowDown=MutableLiveData<Boolean>(true)


    fun onArrownClick(){
        isArrowDown.value=!isArrowDown.value!!
    }

    init {

    }

    fun onItemClick() {
        Log.d("arslan_click_test" ,"on item click")
        onItemClick.invoke()
    }
}