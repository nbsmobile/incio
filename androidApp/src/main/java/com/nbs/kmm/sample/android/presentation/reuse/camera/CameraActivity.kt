package com.nbs.kmm.sample.android.presentation.reuse.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.nbs.kmm.sample.android.presentation.reuse.camera.CameraActivity.CameraMode.BACK
import com.nbs.kmm.sample.android.R
import com.nbs.kmm.sample.android.utils.showAlertDialog
import com.nbs.kmm.sample.android.utils.util.disable
import com.nbs.kmm.sample.android.utils.util.enable
import com.nbs.kmm.sample.android.utils.util.getCompressedBitmap
import com.nbs.kmm.sample.android.utils.util.gone
import com.nbs.kmm.sample.android.utils.util.saveBitmapFile
import com.nbs.kmm.sample.android.utils.util.setColoredStatusBar
import com.nbs.kmm.sample.android.utils.util.setFull
import com.nbs.kmm.sample.android.utils.util.setPortraitImage
import com.nbs.kmm.sample.android.utils.util.visible
import com.yalantis.ucrop.UCrop
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.selector.back
import io.fotoapparat.selector.front
import io.fotoapparat.selector.off
import io.fotoapparat.selector.torch
import io.fotoapparat.view.CameraView
import io.fotoapparat.view.FocusView
import java.io.File
import java.util.*

class CameraActivity : AppCompatActivity() {

    companion object {

        const val PHOTO_REQUEST_CODE = 40
        const val PHOTO_RESULT_PATH = "PHOTO_RESULT"
        const val CAMERA_MODE = "CAMERA_MODE"

        @JvmStatic
        fun startFromDetail(context: Activity, cameraMode: CameraMode = BACK, requestCode: Int = PHOTO_REQUEST_CODE) {
            val starter = Intent(context, CameraActivity::class.java)
            starter.putExtra(CAMERA_MODE, cameraMode)
            context.startActivityForResult(starter, requestCode)
        }

        fun Fragment.startCameraActivityForResult(
            cameraMode: CameraMode = BACK,
            requestCode: Int = PHOTO_REQUEST_CODE
        ) {
            val starter = Intent(context, CameraActivity::class.java)
            starter.putExtra(CAMERA_MODE, cameraMode)
            startActivityForResult(starter, requestCode)
        }

        fun startCameraWithId(
            activity: Activity,
            requestCode: Int,
            id: Int,
            cameraMode: CameraMode = BACK
        ) {
            activity.startActivityForResult(getIntentWithId(activity, id, cameraMode), requestCode)
        }

        private fun getIntentWithId(
            context: Context?,
            id: Int?,
            cameraMode: CameraMode = BACK
        ): Intent {
            val intent = Intent(context, CameraActivity::class.java)
            intent.putExtra(CAMERA_MODE, cameraMode)
            intent.putExtra("SCHEMA_ID", id)
            return intent
        }
    }

    enum class CameraMode {
        FRONT,
        BACK
    }

    private lateinit var camera: Fotoapparat
    private var isFrontCameraActive: Boolean = false
    private var soundPool: SoundPool? = null
    private lateinit var photoResult: io.fotoapparat.result.PhotoResult

    private val schemaId: Int? by lazy { intent?.getIntExtra("SCHEMA_ID", 0) }
    private var cameraShutter = 0

    private val cameraView: CameraView by lazy {
        findViewById(R.id.cameraView)
    }

    private val focusView: FocusView by lazy {
        findViewById<FocusView>(R.id.focusView)
    }

    private val btnBack: AppCompatImageButton by lazy {
        findViewById<AppCompatImageButton>(R.id.btnBack)
    }

    private val btnCapture: AppCompatImageButton by lazy {
        findViewById<AppCompatImageButton>(R.id.btnCapture)
    }

    private val btnFlash: AppCompatImageButton by lazy {
        findViewById<AppCompatImageButton>(R.id.btnFlash)
    }

    private val btnReverse: AppCompatImageButton by lazy {
        findViewById<AppCompatImageButton>(R.id.btnReverse)
    }

    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progress_bar)
    }

    private var isFlashOn: Boolean = false

    private var cameraMode: CameraMode = BACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        setColoredStatusBar(R.color.colorBlack, true)

        initIntent()

        if (checkCameraPermission(this)) {
            initCamera()
            initAction()
        } else {
            requestCameraPermission(this)
        }
    }

    private fun initIntent() {
        cameraMode = intent.getSerializableExtra(CAMERA_MODE) as CameraMode
        isFrontCameraActive = cameraMode != BACK
    }

    private fun initAction() {
        btnBack.setOnClickListener {
            onBackPressed()
        }

        btnCapture.setOnClickListener {
            if (checkWriteStoragePermission(this)) {
                capturePhoto()
            } else {
                requestStoragePermission(this)
            }
        }

        btnFlash.setOnClickListener {
            turnOnOrTurnOffFlash()
        }

        btnReverse.setOnClickListener {
            isFlashOn = false
            if (isFrontCameraActive) {
                isFrontCameraActive = false
                camera.switchTo(back(), CameraConfiguration())
            } else {
                isFrontCameraActive = true
                camera.switchTo(front(), CameraConfiguration())
            }
        }
    }

    private fun turnOnOrTurnOffFlash() {
        val newConfiguration: UpdateConfiguration?
        var imgAssetFlash = 0

        if (isFlashOn) {
            this.isFlashOn = false
            newConfiguration = UpdateConfiguration(
                flashMode = off()
            )
            imgAssetFlash = R.drawable.ic_flash_light_off
        } else {
            this.isFlashOn = true
            newConfiguration = UpdateConfiguration(
                flashMode = torch()
            )
            imgAssetFlash = R.drawable.ic_flash_light_on
        }

        btnFlash.setImageResource(imgAssetFlash)
        camera.updateConfiguration(newConfiguration)
    }

    override fun onStart() {
        super.onStart()
        if (checkCameraPermission(this)) {
            camera.start()
        }
    }

    override fun onStop() {
        if (checkCameraPermission(this)) {
            soundPool?.stop(cameraShutter)
            soundPool?.release()
            camera.stop()
        }
        super.onStop()
    }

    private fun capturePhoto() {
        soundPool?.play(cameraShutter, 1f, 1f, 1, 0, 1f)

        btnCapture.disable()

        photoResult = camera.autoFocus().takePicture()

        progressBar.visible()

        val uuid = UUID.randomUUID().toString().replace("-", "")

        val fileName = "temp_$uuid.jpeg"
        val directory = getTempImageTemporaryDirectory()

        val croppedFile = savePhotoResultToStorage(directory, fileName)
        photoResult.saveToFile(croppedFile)

        photoResult
            .toBitmap()
            .whenAvailable {
                btnCapture.enable()
                progressBar.gone()

                startImageCropping(croppedFile)
            }
    }

    private fun startImageCropping(croppedFile: File) {
        val crop: UCrop = UCrop.of(Uri.fromFile(croppedFile), Uri.fromFile(croppedFile))

        crop.withOptions(UCrop.Options().apply {
            setActiveControlsWidgetColor(
                ContextCompat.getColor(
                    this@CameraActivity,
                    R.color.colorPrimary
                )
            )
            setToolbarColor(ContextCompat.getColor(this@CameraActivity, R.color.colorPrimary))
            setStatusBarColor(ContextCompat.getColor(this@CameraActivity, R.color.colorPrimary))
            setToolbarWidgetColor(ContextCompat.getColor(this@CameraActivity, R.color.colorWhite))
        }).start(this)
    }

    private fun initCamera() {
        window.setFull()

//        if (isFaceOcr) imgFaceGuide.visible()
//        else imgFaceGuide.gone()

        camera = setupCameraFotoApparat(
            context = this,
            cameraView = cameraView,
            cameraMode = cameraMode,
            focusView = focusView
        ) {
             showAlertDialog(this@CameraActivity, it.message ?: "Camera Error")
        }
        setupShutterSound()
        camera.start()
    }

    private fun setupShutterSound() {
        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
                .setMaxStreams(10)
                .build()
        } else {
            SoundPool(10, AudioManager.STREAM_SYSTEM, 1)
        }
        cameraShutter = soundPool?.load(this, R.raw.camera_shutter, 1) ?: 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            data?.let {
                val resultUri = UCrop.getOutput(it)

                val bitmap = getCompressedBitmap(resultUri?.path.orEmpty()).setPortraitImage(resultUri?.path.orEmpty())
                bitmap?.saveBitmapFile(resultUri?.path.orEmpty())

                val intent = Intent()
                intent.putExtra(
                    PHOTO_RESULT_PATH, PhotoResult(
                        resultFile =  File(
                            resultUri?.path.orEmpty()
                        )
                    )
                )
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initIntent()
                    initCamera()
                    initAction()
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.CAMERA
                        )
                    ) {
                        showRationaleDialog(
                            "Fungsi pengambilan foto tidak bekerja jika aplikasi tidak diizinkan akses ke Kamera",
                                getString(R.string.action_repeat)
                        ) {
                            requestCameraPermission(this)
                        }
                    } else {
                        showRationaleDialog(
                            "Anda harus mengizinkan Juara Mobile bisa akses kamera",
                            getString(R.string.action_open_setting)
                        ) {
                            openSettingsPage()
                        }
                    }
                }
            }

            REQUEST_STORAGE_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    capturePhoto()
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        showRationaleDialog(
                            getString(R.string.label_message_access_write_external_storage),
                            getString(R.string.action_repeat)
                        ) {
                            requestStoragePermission(this)
                        }
                    } else {
                        showRationaleDialog(
                            getString(R.string.label_message_when_access_write_external_storage_denied),
                            getString(R.string.action_open_setting)
                        ) {
                            openSettingsPage()
                        }
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun openSettingsPage() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        settingsIntent.data = uri
        startActivityForResult(settingsIntent, 7)
    }

    private fun showRationaleDialog(message: String, postiveText: String, onRetry: () -> Unit) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setNegativeButton("Tutup") { dialog, _ ->
                dialog.dismiss()
                finish()
            }.setPositiveButton(postiveText) { dialog, _ ->
                dialog.dismiss()
                onRetry.invoke()
            }.show()
    }
}