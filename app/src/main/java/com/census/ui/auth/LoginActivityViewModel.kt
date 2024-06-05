package com.census.ui.auth
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.census.data.request.LoginRequest
import com.census.data.response.census.User
import com.census.network.ApiState
import com.census.ui.base.BaseActivity
import com.census.ui.base.BaseViewModel
import com.census.ui.repository.AuthRepository
import com.census.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    fun login(

        request: LoginRequest,
        onSuccess: (message: String?,user: User?) -> Unit,
        onFailure: (message: String?) -> Unit
    ) {
        viewModelScope.launch {
            repository.callLoginApi(request).collect {
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
                            onSuccess.invoke(it.response?.message,it.response?.user)
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