package com.census.ui.blockverification

import androidx.annotation.IntDef

@Target(AnnotationTarget.TYPE)
@IntDef(
    BlockVerificationActivity.ON_BACK_PRESS,
    BlockVerificationActivity.ON_CAPTURE_LOCATION_CLICK,
    BlockVerificationActivity.ON_PROCEED_CLICK



)
annotation class BlockVerificationActivityClickEvents