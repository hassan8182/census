package com.census.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun onBind(position: Int)

}