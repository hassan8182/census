package com.census.ui.reuse

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import com.census.ui.base.BaseActivity
import com.census.BR
import com.census.R
import com.census.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class ReuseActivity :
    BaseActivity<ReuseActivityViewModel, ActivityDashboardBinding>(R.layout.activity_dashboard) {

    private lateinit var drawerToggle: ActionBarDrawerToggle


    companion object {

        const val ON_BACK_PRESS = 0
        const val ON_CLICK_CLICK=1


        fun newIntent(
            context: Context
        ): Intent {
            val intent = Intent(context, ReuseActivity::class.java)
            return intent
        }
    }


    override val viewModel: ReuseActivityViewModel by viewModels()

    override fun getBindingVariable() = BR.viewModel


    override fun onNetworkConnected() {

    }


    override fun initUi() {
        viewModel.event.observe(this) {
            when (it) {


                ON_BACK_PRESS -> {
                    finish()
                }
                ON_CLICK_CLICK->{


                }
            }
        }

        updateStatusBar()
        setStatusBarTextColor(false)

    }






}