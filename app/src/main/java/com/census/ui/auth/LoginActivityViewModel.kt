package com.census.ui.auth
import androidx.lifecycle.MutableLiveData
import com.census.ui.base.BaseViewModel
import com.census.ui.repository.AuthRepository
import com.census.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var repository: AuthRepository

    val isErrorTextShowing = MutableLiveData(false)
    val event = SingleLiveEvent<@LoginActivityClickEvents Int>()


    fun onLoginClick() {
        event.value = LoginActivity.ON_LOGIN_CLICK
    }


}