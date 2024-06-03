package com.census.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */
class ResourceProvider(var context: Context) {

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    fun getString(resId: Int, value: String): String {
        return context.getString(resId, value)
    }

    fun getDimension(resId: Int): Float {
        return context.resources.getDimension(resId)
    }



    fun getInteger(resId: Int): Int {
        return context.resources.getInteger(resId)
    }

    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }
}