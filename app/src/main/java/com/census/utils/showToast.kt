package com.census.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * @Author: Arslan Bhutta
 * @Date: 10/3/22
 */


/**
 * Can show [Toast] from every [Activity].
 */
fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

/**
 * Returns Color from resource.
 * @param id Color Resource ID
 */

fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun <T> Activity.launchActivity(
    context: Context,
    activityName: Class<T>,
    isFinish: Boolean = false,
    bundle: Bundle? = null
) {
    val intent = Intent(context, activityName)
    bundle?.let {
        intent.putExtras(bundle)
    }
    this.startActivity(intent)
    if (isFinish)
        this.finish()
}


fun Activity.recyclerViewItemSeparator(activity: FragmentActivity, @DimenRes margin: Int): Int {
    return activity.resources.getDimensionPixelSize(margin)
}

fun Activity.setStatusBarColor(statusBarColor: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = statusBarColor
    }
}
