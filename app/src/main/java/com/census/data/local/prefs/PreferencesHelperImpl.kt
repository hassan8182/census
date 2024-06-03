package com.census.data.local.prefs

import android.content.Context
import com.census.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */
class PreferencesHelperImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferencesHelper {

    private val mPrefs =
        context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    override fun getLoginUserType(): String {
        return mPrefs.getString(LOGIN_USER_TYPE, "").toString()
    }

    override fun getLoginType(): PreferencesHelper.LoginType {
        return when (mPrefs.getInt(LOGIN_TYPE, 0)) {
            0 -> PreferencesHelper.LoginType.NONE
            1 -> PreferencesHelper.LoginType.TRAINEE
            2 -> PreferencesHelper.LoginType.ADVISOR
            else -> PreferencesHelper.LoginType.SUPER_ADMIN
        }
    }

    override fun setLoginType(login: PreferencesHelper.LoginType) {
        mPrefs.edit().putString(
            LOGIN_USER_TYPE,
            when (login) {
                PreferencesHelper.LoginType.TRAINEE -> Constants.LOGIN_USER_TYPE_TRAINEE
                PreferencesHelper.LoginType.ADVISOR -> Constants.LOGIN_USER_TYPE_ADVISOR
                PreferencesHelper.LoginType.SUPER_ADMIN -> Constants.LOGIN_USER_TYPE_SUPER_ADMIN
                else -> ""
            }
        ).apply()
        mPrefs.edit().putInt(LOGIN_TYPE, login.ordinal).apply()
    }


    companion object {
        private const val LOGIN_TYPE = "LOGIN_TYPE"
        private const val LOGIN_USER_TYPE = "LOGIN_USER_TYPE"
    }
}