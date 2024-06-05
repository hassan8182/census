package com.census.ui.base

import androidx.lifecycle.ViewModel
import com.census.data.local.prefs.PreferencesHelper
import com.census.data.managers.DataManager
import com.census.data.response.census.User
import com.census.utils.Constants
import com.census.utils.SingleLiveEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    @Inject
    lateinit var dataManager: DataManager
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    val baseEvent = SingleLiveEvent<@BaseEvents Int>()


    fun isUserLoggedIn(): Boolean {
        val loginType = dataManager.getPreferencesHelper().getLoginType()
        return loginType == PreferencesHelper.LoginType.SUPER_ADMIN || loginType == PreferencesHelper.LoginType.TRAINEE || loginType == PreferencesHelper.LoginType.ADVISOR
    }

    fun isSuperAdminLoggedIn(): Boolean {
        return dataManager.getPreferencesHelper()
            .getLoginType() == PreferencesHelper.LoginType.SUPER_ADMIN
    }

    open fun isTraineeLoggedIn(): Boolean {
        return dataManager.getPreferencesHelper()
            .getLoginType() == PreferencesHelper.LoginType.TRAINEE
    }

    fun isAdvisorLoggedIn(): Boolean {
        return dataManager.getPreferencesHelper()
            .getLoginType() == PreferencesHelper.LoginType.ADVISOR
    }


    open fun onUserLogout() {
        dataManager.getPreferencesHelper().setLoginType(PreferencesHelper.LoginType.NONE)
    }


    open fun onUserLoggedInSuccessfully(user: User?): Boolean {
        if (user == null) return false

        return true
    }

    open fun getLoggedInUserType(user: User): PreferencesHelper.LoginType? {

//        if (user.role.isNullOrEmpty()) return null
//        if (user.role.equals(Constants.LOGIN_USER_TYPE_ADVISOR)) return PreferencesHelper.LoginType.ADVISOR
//        if (user.role.equals(Constants.LOGIN_USER_TYPE_TRAINEE)) return PreferencesHelper.LoginType.TRAINEE
//        if (user.role.equals(Constants.LOGIN_USER_TYPE_SUPER_ADMIN)) return PreferencesHelper.LoginType.SUPER_ADMIN

        return null

    }

    override fun onCleared() {
        mCompositeDisposable.dispose()

        //Set null to these variable to deAllocate memory.
        //mSchedulerProvider = null;
        //mDataManager = null;
        //mNavigator = null;
        //System.gc();
        //System.runFinalization();
        super.onCleared()
    }

    open fun getCompositeDisposable(): CompositeDisposable? {
        return mCompositeDisposable
    }
}