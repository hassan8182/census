package com.census.ui.blocks

import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.census.ui.base.BaseActivity
import com.census.BR
import com.census.CensusApp
import com.census.R
import com.census.databinding.ActivityBlockSelectionBinding
import com.census.databinding.ActivityDashboardBinding
import com.census.ui.blocks.adapter.BlockSelectionAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BlockSelectionActivity :
    BaseActivity<BlockSelectionActivityViewModel, ActivityBlockSelectionBinding>(R.layout.activity_block_selection) {
    private lateinit var adapterBlockSelection: BlockSelectionAdapter

    private lateinit var drawerToggle: ActionBarDrawerToggle


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

//        setRvBlockSelection()
        updateStatusBar()
        setStatusBarTextColor(false)

    }



/*    private fun setRvBlockSelection() {

        adapterBlockSelection = BlockSelectionAdapter()
        adapterBlockSelection.setListener(object : BlockSelectionAdapter.BasicInterface {
            override fun onItemClick(categoryId: String) {

            }

        })
        bindings.rvBlockSelection.adapter = adapterBlockSelection
        adapterBlockSelection.notifyDataSetChanged()

    }*/


}