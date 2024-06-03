package com.census.utils.typeface

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.census.R

object CustomTypefaces {
    fun getPoppinsRegularTypeface(context: Context): Typeface {
        return ResourcesCompat.getFont(context, R.font.poppins_regular)!!
    }
    fun getPoppinsMediumTypeface(context: Context): Typeface {
        return ResourcesCompat.getFont(context,R.font.poppins_medium)!!
    }

    fun getPoppinsSemiTypeface(context: Context): Typeface {
        return ResourcesCompat.getFont(context,R.font.poppins_semibold)!!
    }
}