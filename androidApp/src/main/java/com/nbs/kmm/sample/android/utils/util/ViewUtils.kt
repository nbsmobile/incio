package com.nbs.kmm.sample.android.utils.util

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}


fun View.disable() {
    this.isEnabled = false
}


fun View.enable() {
    this.isEnabled = true
}
