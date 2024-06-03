package com.census.ui.base

import android.Manifest
import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.census.R
 import com.census.utils.*


abstract class BaseActivity<VM : BaseViewModel, VB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : AppCompatActivity() {

    private var mProgressDialog: ProgressDialog? = null

    protected lateinit var bindings: VB
    protected lateinit var context: Context
    private var lockScreen = false

    protected abstract val viewModel: VM
    protected abstract fun getBindingVariable(): Int
    abstract fun onNetworkConnected()
    abstract fun initUi()

    val REQUEST_CODE_GALLERY_PERMISSION = 1
    val REQUEST_CODE_MICROPHONE_PERMISSION = 2
    val REQUEST_CODE_CAMERA_PERMISSION = 3
    val REQUEST_CODE_LOCATION_PERMISSION = 4

    companion object {
        const val ON_BACK_PRESS = -11
        const val EXPIRED_USER_TOKEN = -22
        const val SHOW_LOADER = -33
        const val HIDE_LOADER = -44
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        context = this
        setStatusBarTextColor(false)
        bindings = DataBindingUtil.setContentView(this, layoutResId)
        bindings.setVariable(getBindingVariable(), viewModel)
        bindings.lifecycleOwner = this

        val networkMonitoringHelper: NetworkMonitoringHelper =
            NetworkMonitoringHelper.newInstance(this) { runOnUiThread { onNetworkConnected() } }
        networkMonitoringHelper.setLifecycleOwner(this)
        initUi()
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.baseEvent.observe(this@BaseActivity) {
            when (it) {
                ON_BACK_PRESS -> {
                    goBack()
                }
                EXPIRED_USER_TOKEN -> {
                    onUserSessionExpired()
                }
                SHOW_LOADER -> {
                    showLoading()
                }
                HIDE_LOADER -> {
                    hideLoading()
                }
            }
        }
    }

    fun goBack() {
        onBackPressed()
    }

    open fun lockTouch(lock: Boolean) {
        lockScreen = lock
    }

    open fun isLocked(): Boolean {
        return lockScreen
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return try {
            lockScreen || super.dispatchTouchEvent(ev)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Update the SystemUiVisibility depending on whether we want a Light or Dark theme.
     *
     * @param textLight boolean true for light text, false for dark text
     */
    open fun setStatusBarTextColor(textLight: Boolean, windowView: Window = window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowView.decorView.doOnLayout {
                windowView.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val lFlags = windowView.decorView.systemUiVisibility
            windowView.decorView.systemUiVisibility =
                if (textLight) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    open fun showErrorMessage(error: String) {
        lockTouch(false)

        if (NetworkUtils.isNetworkConnected(applicationContext)) {
            showToast(getString(R.string.internet_error))
            //            MessageAlert.showErrorMessage(getRootView(), error)
        } else {
            showToast(error)
//            MessageAlert.showNetworkErrorMessage(getRootView())
        }
        /*
        if (getString(R.string.failed_refresh_token).equals(error)) {
            onSessionExpired()
        }
        */
    }

    private var dialogRootViewCall: (() -> View?)? = null

    private fun getRootView(): View {
        return dialogRootViewCall?.invoke() ?: bindings.root
    }

    open fun setDialogViewLambda(dialogRootViewCall: (() -> View?)?) {
        this@BaseActivity.dialogRootViewCall = dialogRootViewCall
    }

    open fun updateStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            //  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.statusBarColor = Color.TRANSPARENT
            setStatusBarTextColor(
                false
            )
        }
    }


    open fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    open fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    open fun isProgressDialogShowing(): Boolean {
        return mProgressDialog != null && mProgressDialog!!.isShowing
    }

    open fun onUserSessionExpired() {
        viewModel.onUserLogout()
//        val dialogAlert = DialogAlert.getInstance(
//            this,
//            "", getString(R.string.you_are_not_logged_in),
//            resources.getString(R.string.ok),
//            resources.getString(R.string.cancel),
//            { dialog: DialogInterface, _ ->
//                dialog.dismiss()
//                signOut()
//            },
//            true
//        )
//        dialogAlert?.setCancelable(false)
//        dialogAlert?.show()

    }

    private fun signOut() {
//        val intent = SelectUserActivity.newIntent(
//            this
//        )
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finishAffinity()

    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun requestPermissionsSafely(permissions: Array<String?>?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    open fun locationPermissionGranted(): Boolean {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    open fun hasMediaPermissions(): Boolean {
        return hasGalleryPermission() && hasMicrophonePermission() && hasCameraPermission()
    }

    open fun hasDocumentMediaPermissions(): Boolean {
        return hasGalleryPermission() && hasCameraPermission()
    }

    open fun hasGalleryPermission(): Boolean {
        return galleryPermissionGranted()
    }

    open fun galleryPermissionGranted(): Boolean {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    open fun hasCameraPermission(): Boolean {
        return cameraPermissionGranted()
    }

    open fun cameraPermissionGranted(): Boolean {
        return hasPermission(Manifest.permission.CAMERA)
    }

    open fun hasMicrophonePermission(): Boolean {
        return microphonePermissionGranted()
    }

    open fun microphonePermissionGranted(): Boolean {
        return hasPermission(Manifest.permission.RECORD_AUDIO)
    }

    open fun requestGalleryPermission() {
        requestPermissionsSafely(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_CODE_GALLERY_PERMISSION
        )
    }

    open fun requestMicrophonePermission() {
        requestPermissionsSafely(
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_CODE_MICROPHONE_PERMISSION
        )
    }

    open fun requestCameraPermission() {
        requestPermissionsSafely(
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CODE_CAMERA_PERMISSION
        )
    }

    open fun checkRemainingPermissionsAfterMic() {
        if (!hasCameraPermission()) requestCameraPermission()
    }

    open fun checkRemainingPermissionsAfterGallery() {
        if (!hasMicrophonePermission()) requestMicrophonePermission() else if (!hasCameraPermission()) requestCameraPermission()
    }

    open fun checkDocumentRemainingPermissionsAfterGallery() {
        if (!hasCameraPermission()) requestCameraPermission()
    }

    open fun openPermissionActivity() {
        if (!hasGalleryPermission()) requestGalleryPermission() else if (!hasMicrophonePermission()) requestMicrophonePermission() else if (!hasCameraPermission()) requestCameraPermission()
    }

    open fun openLocationPermissionActivity() {
        requestPermissionsSafely(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_LOCATION_PERMISSION
        )
    }

    open fun openDocumentPermissionActivity() {
        if (!hasGalleryPermission()) requestGalleryPermission() else if (!hasCameraPermission()) requestCameraPermission()
    }

    open fun onFragmentDetached(tag: String?) {
        if (supportFragmentManager != null) {
            val fragment = supportFragmentManager.findFragmentByTag(tag)
            if (fragment != null) supportFragmentManager.beginTransaction().remove(fragment)
                .commit()
        }
    }

    open fun hideKeyboard() {
        try {
            val view = window.currentFocus
            if (view != null && view.windowToken != null) {
                val binder = view.windowToken
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binder, 0)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


}
