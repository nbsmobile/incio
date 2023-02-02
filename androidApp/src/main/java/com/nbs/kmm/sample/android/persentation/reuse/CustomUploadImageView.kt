package com.nbs.kmm.sample.android.persentation.reuse

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.nbs.kmm.sample.android.R
import com.nbs.kmm.sample.android.databinding.LayoutCustomUploadImageViewBinding
import com.nbs.kmm.sample.android.persentation.reuse.camera.CameraActivity
import com.nbs.kmm.sample.android.persentation.reuse.camera.CameraActivity.CameraMode
import com.nbs.kmm.sample.android.persentation.reuse.camera.CameraActivity.Companion.PHOTO_REQUEST_CODE
import com.nbs.kmm.sample.android.persentation.reuse.camera.CameraActivity.Companion.startCameraActivityForResult
import com.nbs.kmm.sample.android.utils.util.ImagePickerUtil
import com.nbs.kmm.sample.android.utils.util.gone
import com.nbs.kmm.sample.android.utils.util.setImageUrl
import com.nbs.kmm.sample.android.utils.util.visible

class CustomUploadImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val activity = context as Activity

    private var fragment: Fragment? = null

    private val imagePickerUtil: ImagePickerUtil by lazy {
        ImagePickerUtil()
    }

    private val binding: LayoutCustomUploadImageViewBinding =
        LayoutCustomUploadImageViewBinding.inflate(
            LayoutInflater.from(context), this
        )

    fun setFragmentResult(fragment: Fragment) {
        this.fragment = fragment
    }

    fun openCamera(
        requestCode: Int = PHOTO_REQUEST_CODE,
        cameraMode: CameraMode = CameraMode.BACK
    ) {
        if (fragment != null) {
            fragment!!.startCameraActivityForResult(cameraMode, requestCode)
        } else {
            CameraActivity.startFromDetail(activity, cameraMode, requestCode)
        }
    }

    fun openImagePicker(requestCode: Int) {
        if (fragment != null) {
            imagePickerUtil.openImagePicker(fragment!!, requestCode)
        } else {
            imagePickerUtil.openImagePicker(activity, requestCode)
        }
    }

    fun setUploadIcon(image: Int) {
        binding.imgCamera.setImageResource(image)
    }

    fun setResultImage(path: String) {
        with(binding) {
            imgResult.visible()
            imgResult.setImageUrl(context, path, R.drawable.ic_error_photo, true)
            imgCamera.gone()
            uploadImageContainer.background = null
        }
    }

    fun isImageExist(): Boolean {
        return binding.imgResult.isVisible
    }
}