package com.census.ui.common.splash

import com.census.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {



//    // -------------------------------------Account Completion--------------------------------------
//
//    fun isInterestAdded(): Boolean {
//
//        val user = dataManager.getPreferencesHelper().getUser()
//        return user?.onboard != null && user.onboard!!.customer_interest_added!!
//    }
//
//    fun isScheduleAdded(): Boolean {
//        val user = dataManager.getPreferencesHelper().getUser()
//        return user?.onboard != null && user.onboard!!.availabilty_added!!
//
//    }
//
//    fun isProfessionAdded(): Boolean {
//        val user = dataManager.getPreferencesHelper().getUser()
//        return user?.onboard != null && user.onboard!!.profession_added!!
//    }
//
//    fun isProfileInformationAdded(): Boolean {
//        val user = dataManager.getPreferencesHelper().getUser()
//        return user?.onboard != null && user.onboard!!.profile_information_added!! && !user.career_highlight.isNullOrEmpty()
//    }

    // ------------------------------------------Auto Login API-------------------------------------
//
//    fun autoLoginApiCall(
//        onApiSuccess: (response: User?) -> Unit,
//        onApiError: () -> Unit
//    ) {
//        viewModelScope.launch {
//
//            authRepository.callAutoLoginApi().collect { state ->
//                when (state) {
//                    is ApiState.Loading -> {
//                        if (isAnimationEnded.value == true)
//                            baseEvent.value = BaseActivity.SHOW_LOADER
//                    }
//                    is ApiState.Success -> {
//                        baseEvent.value = BaseActivity.HIDE_LOADER
//
//                        state.response?.data.also { user ->
//                            // Update the Current Login User with New Data
//                            if (onUserLoggedInSuccessfully(user = user)) {
//                                onApiSuccess(user)
//                            } else {
//                                onApiError.invoke()
//                            }
//                        }
//                    }
//                    is ApiState.Failure -> {
//                        baseEvent.value = BaseActivity.HIDE_LOADER
//                        onApiError.invoke()
//                    }
//                    is ApiState.OnUnAuthorizeTokenFailed -> {
//                        baseEvent.value = BaseActivity.HIDE_LOADER
//                        baseEvent.value = BaseActivity.EXPIRED_USER_TOKEN
//                    }
//                }
//            }
//        }
//    }

    companion object {
        private const val TAG = "SplashViewModel"
    }
}