package com.census.ui.blocks.adapter

import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.census.CensusApp.Companion.context
import com.census.R
import com.census.databinding.ItemBlockSelectionBinding
import com.census.ui.base.BaseViewHolder


class BlockSelectionAdapter() : RecyclerView.Adapter<BaseViewHolder>() {



    private var basicInterface: BasicInterface? = null
    fun setListener(basicInterface: BasicInterface) {
        this.basicInterface = basicInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val bindingItemBlockSelection = ItemBlockSelectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BlockSelectionViewHolder(bindingItemBlockSelection)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return 3
    }


    inner class BlockSelectionViewHolder(
        private val binding: ItemBlockSelectionBinding,
    ) : BaseViewHolder(binding.root) {


        override fun onBind(position: Int) {


            val viewModel = BlockSelectionItemViewModel(item = "", position, onItemClick = {
                Log.d("arslan_click_test", "on item click adapter $basicInterface")
//                basicInterface?.onItemClick(categoryList[position].categoryId.toString())
                showPopupMenu2(binding.ivDots)
            })


            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

    }


    companion object {
        const val VIEW_TYPE_SEARCH_EMPLOYEE = 10
        const val VIEW_TYPE_SEARCH_RECORD = 11
    }

    interface BasicInterface {
        fun onItemClick(canteenId: String)
    }
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(context, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.block_status_item, popup.menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.block_active -> {
                    Toast.makeText(context, "Settings clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.block_complete -> {
                    Toast.makeText(context, "About clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
    private fun showPopupMenu2(view: View) {
        val wrapper = ContextThemeWrapper(context, R.style.CustomPopupMenu)
        val popup = PopupMenu(wrapper, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.block_status_item, popup.menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.block_active -> {
                    Toast.makeText(context, "Settings clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.block_complete -> {
                    Toast.makeText(context, "About clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}