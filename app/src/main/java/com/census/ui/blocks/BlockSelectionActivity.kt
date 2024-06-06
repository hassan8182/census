package com.census.ui.blocks

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.census.ui.base.BaseActivity
import com.census.BR
import com.census.CensusApp
import com.census.R
import com.census.data.local.db.DatabaseRepository
import com.census.databinding.ActivityBlockSelectionBinding
import com.census.databinding.ActivityDashboardBinding
import com.census.ui.blocks.adapter.BlockSelectionAdapter
import com.census.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class BlockSelectionActivity :
    BaseActivity<BlockSelectionActivityViewModel, ActivityBlockSelectionBinding>(R.layout.activity_block_selection) {
    private lateinit var adapterBlockSelection: BlockSelectionAdapter

    private lateinit var drawerToggle: ActionBarDrawerToggle

    @Inject
    lateinit var databaseRepository: DatabaseRepository


    companion object {

        const val ON_BACK_PRESS = 0
        const val ON_CLICK_CLICK = 1


        fun newIntent(
            context: Context
        ): Intent {
            val intent = Intent(context, BlockSelectionActivity::class.java)
            return intent
        }
    }


    override val viewModel: BlockSelectionActivityViewModel by viewModels()

    override fun getBindingVariable() = BR.viewModel


    override fun onNetworkConnected() {

    }


    override fun initUi() {
        viewModel.event.observe(this) {
            when (it) {


                ON_BACK_PRESS -> {
                    finish()
                }

                ON_CLICK_CLICK -> {

                }

            }
        }

        setRvBlockSelection()
        updateStatusBar()
        setStatusBarTextColor(false)
        observeList()


    }

    private fun observeList(){
        viewModel.syncData.observe(this) { syncData ->
            adapterBlockSelection.syncDataList = syncData
            adapterBlockSelection.notifyDataSetChanged()
        }
    }

    private fun setRvBlockSelection() {

        adapterBlockSelection = BlockSelectionAdapter()
        adapterBlockSelection.setListener(object : BlockSelectionAdapter.BasicInterface {
            override fun onItemClick(categoryId: String) {

            }

            override fun onInActiveClick(id: Int, active: Int) {
                showLoading()
                lifecycleScope.launch {
                    databaseRepository.updateSyncDataActive(id, active)
                    hideLoading()
                    showToast("Block Inactive")
                }
            }

            override fun onActiveClick(id: Int, active: Int) {
                showLoading()
                lifecycleScope.launch {
                    databaseRepository.updateSyncDataActive(id,active)
                    hideLoading()
                    showToast("Block Active")
                }

            }

            override fun onCompleteClick(id: Int, active: Int) {
                showLoading()
                lifecycleScope.launch {
                    databaseRepository.updateSyncDataActive(id,active)
                    hideLoading()
                    showToast("Block is completed")
                }

            }

            override fun onCancelClick(id: Int, active: Int) {
                showLoading()
                lifecycleScope.launch {
                    databaseRepository.updateSyncDataActive(id,active)
                    hideLoading()
                    showToast("Block cancelled")
                }

            }

        })
        bindings.rvBlockSelection.adapter = adapterBlockSelection
        adapterBlockSelection.notifyDataSetChanged()

    }


}