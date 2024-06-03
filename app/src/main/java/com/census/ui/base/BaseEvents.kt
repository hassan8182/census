package com.census.ui.base

import androidx.annotation.IntDef

/**
 * @Author: Arslan Bhutta
 * @Date: 11/4/22
 */
@Target(AnnotationTarget.TYPE)
@IntDef(
    BaseActivity.ON_BACK_PRESS,
    BaseActivity.EXPIRED_USER_TOKEN,
    BaseActivity.SHOW_LOADER,
    BaseActivity.HIDE_LOADER
)
annotation class BaseEvents
