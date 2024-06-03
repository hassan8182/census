package com.census.ui.common.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.census.BR
import com.census.R
import com.census.databinding.ActivitySplashBinding
import com.census.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity :
    BaseActivity<SplashViewModel, ActivitySplashBinding>(R.layout.activity_splash) {

    override val viewModel: SplashViewModel by viewModels()
    override fun getBindingVariable() = BR.viewModel
    private var isReOpenAppFromRecent = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            val isNotTaskRoot = (!isTaskRoot
                    && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && Intent.ACTION_MAIN == intent.action)
            if (isNotTaskRoot /*&& intent.extras == null*/) {
                finish()
                return
            }
        }
        setUpStatusBar()
        setUp()
    }

    override fun initUi() = Unit

    override fun onNetworkConnected() {

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setUp()
    }

    // -----------------------------------Setup User Interface--------------------------------------

    private fun isOpenFromRecent(): Boolean {

        Log.d(TAG, "Flags -> ${intent.flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY != 0}")

        return intent.flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY != 0
    }

    private fun setUpStatusBar() {
        updateStatusBar()
        setStatusBarTextColor(false)
    }

    private fun setUp() {

        isReOpenAppFromRecent = isOpenFromRecent()
        updateStatusBar()
        setStatusBarTextColor(
            textLight = false
        )
        onAnimationCompleted()
//        setupAnimationListener()
    }

/*    private fun setupAnimationListener() {

        bindings.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) = Unit

            override fun onAnimationEnd(animation: Animator) {
                onAnimationCompleted()
            }

            override fun onAnimationCancel(animation: Animator) = Unit
            override fun onAnimationStart(animation: Animator) = Unit
        })

    }*/

    private fun onAnimationCompleted() {

    }


    companion object {
        const val TAG = "SplashActivity"
    }

}