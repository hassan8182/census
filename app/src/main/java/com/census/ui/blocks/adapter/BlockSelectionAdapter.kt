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
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.census.CensusApp.Companion.context
import com.census.R
import com.census.data.local.db.tables.syncdata.SyncData
import com.census.databinding.ItemBlockSelectionBinding
import com.census.ui.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView


class BlockSelectionAdapter() : RecyclerView.Adapter<BaseViewHolder>() {

    var syncDataList: List<SyncData> = listOf()

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
        return syncDataList.size
    }


    inner class BlockSelectionViewHolder(
        private val binding: ItemBlockSelectionBinding,
    ) : BaseViewHolder(binding.root) {


        override fun onBind(position: Int) {
            binding.tvProvinceValue.text = syncDataList[position].provinceName
            binding.tvDistrictValue.text = syncDataList[position].districtName
            binding.tvTehsilValue.text = syncDataList[position].tehsilName
            binding.tvBlockIdValue.text = syncDataList[position].blockId.toString()

            if (syncDataList[position].active == 0) {
               binding.tvBlockStatus.text = "Inactive"
                binding.tvBlockStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.black
                    )
                )
                binding.ivBlockStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )

            } else if (syncDataList[position].active == 1) {
                binding.tvBlockStatus.text = "Active"
                binding.tvBlockStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.incomplete
                    )
                )
                binding.ivBlockStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.incomplete
                    )
                )

            } else if (syncDataList[position].active == 2) {
                binding.tvBlockStatus.text = "Complete"
                binding.tvBlockStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.completed
                    )
                )
                binding.ivBlockStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.completed
                    )
                )

            } else if (syncDataList[position].active == 3) {
                binding.tvBlockStatus.text = "Cancel"
                binding.tvBlockStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.red_color_cancel
                    )
                )
                binding.ivBlockStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.red_color_cancel
                    )
                )
            }

            val viewModel = BlockSelectionItemViewModel(item = "", position, onItemClick = {
                Log.d("arslan_click_test", "on item click adapter $basicInterface")
//                basicInterface?.onItemClick(categoryList[position].categoryId.toString())
                showPopupMenu(
                    binding.ivDots,
                    syncDataList[position].id,
                    binding.tvBlockStatus,
                    binding.ivBlockStatus
                )
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
        fun onInActiveClick(id: Int, active: Int)
        fun onActiveClick(id: Int, active: Int)
        fun onCompleteClick(id: Int, active: Int)
        fun onCancelClick(id: Int, active: Int)
    }

    private fun showPopupMenu(
        view: View,
        id: Int?,
        tvBlockStatus: AppCompatTextView,
        ivBlockStatus: ShapeableImageView
    ) {
        val wrapper = ContextThemeWrapper(context, R.style.CustomPopupMenu)
        val popup = PopupMenu(wrapper, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.block_status_item, popup.menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {


                R.id.block_inactive -> {
                    if (tvBlockStatus.text != "Inactive") {
                        id?.let {
                            basicInterface?.onInActiveClick(it, 0)
                            tvBlockStatus.text = "Inactive"
                            tvBlockStatus.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.black
                                )
                            )
                            ivBlockStatus.setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.white
                                )
                            )


                        } ?: run {

                        }
                    }


                    true
                }

                R.id.block_active -> {
                    if (tvBlockStatus.text != "Active") {
                        id?.let {
                            basicInterface?.onActiveClick(it, 1)
                            tvBlockStatus.text = "Active"
                            tvBlockStatus.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.incomplete
                                )
                            )
                            ivBlockStatus.setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.incomplete
                                )
                            )



                        } ?: run {

                        }
                    }

                    true
                }

                R.id.block_complete -> {
                    if (tvBlockStatus.text != "Complete") {
                        id?.let {
                            basicInterface?.onCompleteClick(it, 2)
                            tvBlockStatus.text = "Complete"
                            tvBlockStatus.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.completed
                                )
                            )
                            ivBlockStatus.setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.completed
                                )
                            )

                        } ?: run {

                        }
                    }
                    true
                }

                R.id.block_cancel -> {
                    if (tvBlockStatus.text != "Cancel") {
                        id?.let {
                            basicInterface?.onCancelClick(it, 3)
                            tvBlockStatus.text = "Cancel"
                            tvBlockStatus.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.red_color_cancel
                                )
                            )
                            ivBlockStatus.setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.red_color_cancel
                                )
                            )


                        } ?: run {

                        }
                    }


                    true
                }

                else -> false
            }
        }
        popup.show()
    }
}