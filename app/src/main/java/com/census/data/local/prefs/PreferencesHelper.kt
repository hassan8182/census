package com.census.data.local.prefs

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */
interface PreferencesHelper {
    enum class LoginType(i: Int) {
        NONE(0),
        TRAINEE(1),
        ADVISOR(2),
        SUPER_ADMIN(3)
    }

    /// setLoginUserType not declared as we don't need it..
    ///when user set setLoginType(login: LoginType) , we automatically setLoginUserType
    fun getLoginUserType(): String

    fun getLoginType(): LoginType
    fun setLoginType(login: LoginType)
    fun saveUserItem(userItemString: String)
    fun getUserItem(): String

}