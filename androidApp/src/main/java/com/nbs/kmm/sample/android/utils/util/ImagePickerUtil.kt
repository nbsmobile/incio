package com.nbs.kmm.sample.android.utils.util

import android.app.Activity
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker

class ImagePickerUtil {

    fun openCamera(fragment: Fragment, requestCode: Int) {
        ImagePicker.with(fragment)
            .cameraOnly()
            .start(requestCode)
    }

    fun openCamera(activity: Activity, requestCode: Int) {
        ImagePicker.with(activity)
            .cameraOnly()
            .start(requestCode)
    }

    fun openImagePicker(fragment: Fragment, requestCode: Int) {
        ImagePicker.with(fragment)
            .compress(1000)
            .start(requestCode)
    }

    fun openImagePicker(activity: Activity, requestCode: Int) {
        ImagePicker.with(activity)
            .compress(1000)
            .start(requestCode)
    }
}