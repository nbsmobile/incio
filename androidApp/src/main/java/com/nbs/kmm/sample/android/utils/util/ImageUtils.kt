package com.nbs.kmm.sample.android.utils.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import com.stfalcon.imageviewer.StfalconImageViewer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun Bitmap.setPortraitImage(imagePath: String): Bitmap?{
    val exitInterface = ExifInterface(imagePath)
    val orientation: Int = exitInterface
        .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270f)
        ExifInterface.ORIENTATION_NORMAL -> this
        else -> this
    }
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}

fun Bitmap.saveBitmapFile(path: String) {
    val file = File(path)
    val outStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
    val bitmapData = outStream.toByteArray()

    file.createNewFile()
    val fos = FileOutputStream(file)
    fos.write(bitmapData)
    fos.flush()
    fos.close()
}

fun Bitmap.toByteArray(): ByteArray {
    val bos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, bos)
    return bos.toByteArray()
}

fun getBitmap(filePath: String): Bitmap {
    return BitmapFactory.decodeFile(filePath)
}

fun getCompressedBitmap(filePath: String): Bitmap {
    val bitmap = BitmapFactory.decodeFile(filePath)
    return ImageResizer.reduceBitmapSize(bitmap, 1000000)
}

fun getFileNameFromPath(path: String): String {
    return path.substring(path.lastIndexOf("/") + 1)
}

fun Drawable.toByteArray(resources: Resources): ByteArray {
    val bitmap = (this as BitmapDrawable).bitmap
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun previewImage(context: Context, imageDrawable: Drawable) {
    StfalconImageViewer.Builder(context, mutableListOf(imageDrawable)) { view, image ->
        view.setImageDrawable(image)
    }.withHiddenStatusBar(false).show()
}

fun Bitmap.toFile(path: String): File {
    val file = File(path)
    val outStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
    val bitmapData = outStream.toByteArray()

    file.createNewFile()
    val fos = FileOutputStream(file)
    fos.write(bitmapData)
    fos.flush()
    fos.close()

    return file
}

fun Context.getCompressedFileFromGallery(imageFile: File): File {
    val uuid = UUID.randomUUID().toString().replace("-", "")
    val tempFileName = "temp_$uuid.${imageFile.extension}"
    val finalFile = File(this.cacheDir, tempFileName)
    imageFile.copyTo(finalFile, overwrite = true)
    val bitmap = getCompressedBitmap(finalFile.path)
    bitmap.saveBitmapFile(finalFile.path)
    return finalFile
}