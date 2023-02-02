package com.nbs.kmm.sample.android.presentation.post

import android.content.Context
import android.content.Intent
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityPostStoryBinding
import com.nbs.kmm.sample.android.presentation.reuse.camera.CameraActivity
import com.nbs.kmm.sample.android.presentation.reuse.camera.PhotoResult
import com.nbs.kmm.sample.android.utils.showToast

class PostStoryActivity : SampleBaseActivity<ActivityPostStoryBinding>() {
    companion object {
        const val UPLOAD_PHOTO_STORY_CODE = 3434

        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, PostStoryActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityPostStoryBinding.inflate(layoutInflater)

    override fun initIntent() {}

    override fun initUI() {
        setupToolbar(binding.layoutToolbar.toolbar, "Tambah Story", true)
    }

    override fun initAction() {
        with(binding) {
            cuivPhotoStory.setOnClickListener {
                cuivPhotoStory.openCamera(UPLOAD_PHOTO_STORY_CODE)
            }
        }
    }

    override fun initProses() {}
    override fun initObservers() {}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            data?.let {
                val result = it.getSerializableExtra(CameraActivity.PHOTO_RESULT_PATH) as PhotoResult

                if (requestCode == UPLOAD_PHOTO_STORY_CODE) {
                    val profileImagePath = result.resultFile.absolutePath
                    showToast(profileImagePath)
                }
            }
        }
    }
}