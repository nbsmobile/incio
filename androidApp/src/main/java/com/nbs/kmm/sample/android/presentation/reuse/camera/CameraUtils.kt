package com.nbs.kmm.sample.android.presentation.reuse.camera

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import io.fotoapparat.Fotoapparat
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.log.logcat
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.*
import io.fotoapparat.view.CameraView
import io.fotoapparat.view.FocusView
import java.io.File

fun setupCameraFotoApparat(
    context: Context,
    cameraView: CameraView,
    cameraMode: CameraActivity.CameraMode,
    focusView: FocusView,
    onCameraError: (CameraException) -> Unit
): Fotoapparat {
    return Fotoapparat.with(context)
        .into(cameraView)
        .focusView(focusView)
        .previewResolution(highestResolution()) //  Preview use highest resolution
        .previewScaleType(ScaleType.CenterCrop) //  Preview fill the view
        .lensPosition(if (cameraMode == CameraActivity.CameraMode.BACK) back() else front()) //  Use back camera
        .photoResolution(
            standardRatio(
                highestResolution()       //  Use Highest Resolution
            )
        )
        .focusMode(
            firstAvailable(
                continuousFocusPicture(),
                autoFocus(),                    // If continuous focus is not available on device, auto focus will be used
                continuousFocusPicture(),       // If even auto focus is not available - fixed focus mode will be used
                fixed()
            )
        )
        .jpegQuality(manualJpegQuality(100))        // Set jpeg quality to 75 of 100
        .logger(logcat())
        .cameraErrorCallback {
            onCameraError.invoke(it)
        }
        .build()
}

fun AppCompatActivity.getTempImageTemporaryDirectory(): File {
    val contextWrapper = ContextWrapper(applicationContext)
    val directory = contextWrapper.getDir(filesDir.name, Context.MODE_PRIVATE)
    return File("${directory}/images/temporary")
}

fun savePhotoResultToStorage(directory: File, fileName: String): File {
    if (!directory.exists()) {
        directory.mkdirs()
    }
    return File(directory, fileName)
}