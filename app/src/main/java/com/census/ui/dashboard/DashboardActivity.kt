package com.census.ui.dashboard

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.census.ui.base.BaseActivity
import com.census.BR
import com.census.R
import com.census.databinding.ActivityDashboardBinding
import com.census.utils.CommonUtils.showToast
import com.census.utils.showToast
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class DashboardActivity :
    BaseActivity<DashboardActivityViewModel, ActivityDashboardBinding>(R.layout.activity_dashboard) {

    private lateinit var drawerToggle: ActionBarDrawerToggle


    companion object {

        const val ON_BACK_PRESS = 0
        const val ON_CLICK_CLICK=1


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
                ON_CLICK_CLICK->{


                }
            }
        }

        setupDrawer()
        updateStatusBar()
        setStatusBarTextColor(false)

    }

    private fun setupDrawer() {
        val drawerButton: CardView =bindings.ivDrawer
        val drawerLayout: DrawerLayout =findViewById(R.id.drawerLayout)
        val navView: NavigationView =findViewById(R.id.navView)
        drawerToggle= ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_dashboard-> showToast("Dashboard Clicked")

            }
            true
        }
        drawerButton.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)){true}
        return super.onOptionsItemSelected(item)
    }





}