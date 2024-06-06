package com.census.ui.dashboard

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.census.ui.base.BaseActivity
import com.census.BR
import com.census.BuildConfig
import com.census.R
import com.census.data.response.census.User
import com.census.databinding.ActivityDashboardBinding
import com.census.ui.blocks.BlockSelectionActivity
import com.census.ui.blockverification.BlockVerificationActivity
import com.census.utils.CommonUtils
import com.census.utils.NetworkUtils
import com.census.utils.showToast
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity :
    BaseActivity<DashboardActivityViewModel, ActivityDashboardBinding>(R.layout.activity_dashboard) {

    private lateinit var drawerToggle: ActionBarDrawerToggle


    companion object {

        const val ON_BACK_PRESS = 0
        const val ON_SYNC_CLICK = 1


        fun newIntent(
            context: Context
        ): Intent {
            val intent = Intent(context, DashboardActivity::class.java)
            return intent
        }
    }


    override val viewModel: DashboardActivityViewModel by viewModels()

    override fun getBindingVariable() = BR.viewModel


    override fun onNetworkConnected() {

    }


    override fun initUi() {
        viewModel.event.observe(this) {
            when (it) {


                ON_BACK_PRESS -> {
                    finish()
                }

                ON_SYNC_CLICK -> {
                    syncData()

                }
            }
        }

        setupDrawer()
        updateStatusBar()
        setStatusBarTextColor(false)

    }

    private fun setupDrawer() {
        val drawerButton: CardView = bindings.ivDrawer
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)
        drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_dashboard -> showToast("Dashboard Clicked")
                R.id.nav_blocks_selection -> startActivity(BlockSelectionActivity.newIntent(this))
                R.id.nav_new_shop->startActivity(BlockVerificationActivity.newIntent(this))
                R.id.nav_sync_server->syncData()

            }
            true
        }
        drawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


    }

   private fun syncData() {
        val userString = viewModel.dataManager.getPreferencesHelper().getUserItem()
        val userItem = Gson().fromJson(userString, User::class.java)
       callGetSyncData(userItem.id.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {

            true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun callGetSyncData(userId:String) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            CommonUtils.showToast(this, getString(R.string.internet_error))
            return
        }
        viewModel.getSyncData(
            BuildConfig.BASE_URL + "syncMaps/" + userId ,
            onSuccess = { message,syncData ->
                Log.d("syncData","${syncData?.id}")

            }) { message ->
            showToast(message)
        }
    }


}