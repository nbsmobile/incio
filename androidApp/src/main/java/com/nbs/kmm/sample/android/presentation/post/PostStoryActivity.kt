package com.nbs.kmm.sample.android.presentation.post

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.nbs.kmm.sample.android.R
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityPostStoryBinding
import com.nbs.kmm.sample.android.presentation.reuse.camera.CameraActivity
import com.nbs.kmm.sample.android.presentation.reuse.camera.PhotoResult
import com.nbs.kmm.sample.android.utils.data.Resource
import com.nbs.kmm.sample.android.utils.showToast
import com.nbs.kmm.sample.android.utils.util.showErrorAlert
import com.nbs.kmm.sample.android.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PostStoryActivity : SampleBaseActivity<ActivityPostStoryBinding>() {
    companion object {
        const val UPLOAD_PHOTO_STORY_CODE = 3434

        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, PostStoryActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityPostStoryBinding.inflate(layoutInflater)

    private val storyViewModel: StoryViewModel by viewModel()

    private lateinit var selectedFile: File

    override fun initIntent() {}

    override fun initUI() {
        setupToolbar(binding.layoutToolbar.toolbar, "Tambah Story", true)
    }

    override fun initAction() {
        with(binding) {
            cuivPhotoStory.setOnClickListener {
                cuivPhotoStory.openCamera(UPLOAD_PHOTO_STORY_CODE)
            }

            btnPost.setOnClickListener {
                storyViewModel.uploadStory(
                    selectedFile.readBytes(), edtDescription.text.toString()
                )
            }
        }
    }

    override fun initProses() {}

    override fun initObservers() {
        storyViewModel.uploadStoryResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    showToast(getString(R.string.upload_success))
                    finish()
                }

                is Resource.Failure -> {
                    hideLoading()
                    showErrorAlert(it.throwable, it.message.toString())
                }
                else -> {}
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            data?.let {
                val result =
                    it.getSerializableExtra(CameraActivity.PHOTO_RESULT_PATH) as PhotoResult

                selectedFile = result.resultFile

                if (requestCode == UPLOAD_PHOTO_STORY_CODE) {
                    binding.cuivPhotoStory.setResultImage(selectedFile.absolutePath)
                }
            }
        }
    }
}