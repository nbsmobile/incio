package com.nbs.kmm.sample.android.utils.util

import android.view.Window
import android.view.WindowManager

fun Window.setFull(){
    this.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}

fun Window.clearFull(){
    this.clearFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}