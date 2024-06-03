package com.census.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions3.Permission
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.disposables.Disposable

class PermissionsHelper {
    private var mRxPermissions: RxPermissions
    private var permissionListener: PermissionListener<Boolean>? = null

    constructor(activity: FragmentActivity) {
        mRxPermissions = RxPermissions(activity)
    }

    constructor(fragment: Fragment) {
        mRxPermissions = RxPermissions(fragment)
    }

    fun requestPermissions(
        taskListener: PermissionListener<Boolean>?,
        vararg requiredPermissions: String?
    ): Disposable {
        permissionListener = taskListener
        /*
            Add RequestEachCombined method in RxPermission to get combined results.
        */return mRxPermissions
            .requestEachCombined(*requiredPermissions)
            .subscribe { permission: Permission ->  // will emit 2 Permission objects
                if (permission.granted) {
                    permissionListener?.onResponse(true)
                } else if (permission.shouldShowRequestPermissionRationale) {
                    // Denied permission without ask never again
                    permissionListener?.onError()
                } else {
                    // Denied permission with ask never again
                    // Need to go to the settings
                    permissionListener?.onDoNotAskAgain()
                }
            }
    }

    /**
     * This function checks if required permissions are guaranteed or not
     *
     * @return true if guaranteed permissions, false otherwise
     */
    fun hasRequiredPermission(vararg permissions: String?): Boolean {
        var isGranted = false
        for (permission in permissions) {
            isGranted = mRxPermissions.isGranted(permission)
            if (!isGranted) break
        }
        return isGranted
    }
}