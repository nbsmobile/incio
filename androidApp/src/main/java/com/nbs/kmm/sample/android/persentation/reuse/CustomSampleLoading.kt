package com.nbs.kmm.sample.android.persentation.reuse

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.nbs.kmm.sample.android.R

class CustomSampleLoading(context: Context) : Dialog(context) {
    init {
        val layoutParams: WindowManager.LayoutParams? =
            window?.attributes
        layoutParams?.gravity = Gravity.CENTER_HORIZONTAL
        window?.attributes = layoutParams
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        val view: View =
            View.inflate(context, R.layout.layout_sample_loading, null)
        setContentView(view)
    }
}