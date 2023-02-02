package com.nbs.kmm.sample.android.utils.util

import android.app.Activity
import android.os.Build
import android.view.View


fun Activity.setColoredStatusBar(color: Int, isDarkStatusBar: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor = window.decorView

        if (isDarkStatusBar) decor.systemUiVisibility = 0
        else decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = this.resources.getColor(color, this.theme)
    } else {
        window.statusBarColor = this.resources.getColor(color)
    }
}