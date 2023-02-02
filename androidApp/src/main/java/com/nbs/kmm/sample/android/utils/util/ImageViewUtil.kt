package com.nbs.kmm.sample.android.utils.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nbs.kmm.sample.android.R


fun ImageView.setImageUrl(
    imageUrl: String,
    isCenterCrop: Boolean
) {
//    this.load(imageUrl) {
//        placeholder(R.drawable.ic_error_photo)
//    }
        val options = RequestOptions()
        if (isCenterCrop) options.centerCrop()
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_error_photo)
            .apply(options)
            .into(this)
}