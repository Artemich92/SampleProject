package com.sampleproject.utils.helpers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.sampleproject.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

const val MAX_QUALITY = 100

class SaveImageInGalleryHelper private constructor(
    private var context: Context?,
    private var bitmap: Bitmap,
    private var showInGallery: Boolean
) {

    private val scopeSaveImage = CoroutineScope(Dispatchers.IO)

    init {
        scopeSaveImage.launch {
            saveImage()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun saveImage() {
        val name = "IMG_${SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(Date())}.jpg"

        val imageUri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveImageAndroid10(name)
            } else {
                saveImageAndroid9(name, bitmap)
            }
        if (imageUri == null) {
            Toast.makeText(context, "Файл не сохранен", Toast.LENGTH_LONG).show()
        } else if (showInGallery) {
            openGallery(imageUri)
        }
        context = null
        scopeSaveImage.cancel()
    }

    // Сохранение на API Level 29 и выше
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageAndroid10(name: String): Uri? {
        val dir =
            "${Environment.DIRECTORY_PICTURES}/REAL_COSMETOLOGY"
        val resolver = context?.contentResolver
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, dir)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val imageUri = resolver?.insert(collection, values)

        try {
            imageUri?.let { uri ->
                resolver.openOutputStream(uri)?.let { stream ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, MAX_QUALITY, stream)) {
                        Timber.d("TAG_SAVE_IMAGE", "Failed to save bitmap.")
                    }
                } ?: throw IOException("Failed to get output stream")
            } ?: throw IOException("Failed to insert new MediaStore record.")

            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(imageUri, values, null, null)
        } catch (e: Exception) {
            if (imageUri != null) {
                resolver.delete(imageUri, null, null)
            }
            return null
        }
        return imageUri
    }

    // Сохранение на API Level 28 и ниже
    @Suppress("DEPRECATION")
    private fun saveImageAndroid9(name: String, bitmap: Bitmap): Uri? {
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "REAL_COSMETOLOGY"
        )
        try {
            dir.mkdirs()
        } catch (e: Exception) {
            return null
        }
        val file = File(dir, name)
        try {
            if (!file.exists()) {
                file.createNewFile()
            }
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, MAX_QUALITY, stream)
            stream.close()
        } catch (e: Exception) {
            if (file.exists()) {
                file.delete()
            }
            return null
        }

        val imageUri = Uri.fromFile(file)
        try {
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            mediaScanIntent.data = imageUri
            context?.sendBroadcast(mediaScanIntent)
        } catch (e: Exception) {
            Timber.d("TAG_SAVE_IMAGE", "Failed to save image.")
        }

        return context?.let { FileProvider.getUriForFile(it, "${BuildConfig.APPLICATION_ID}.provider", file) }
    }

    private fun openGallery(uri: Uri) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(uri, "image/*")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        context?.startActivity(intent)
    }

    class Builder {

        private lateinit var context: Context
        private lateinit var bitmap: Bitmap
        private var showInGallery: Boolean = false

        fun with(context: Context) = apply { this.context = context }

        fun from(imageView: ImageView) = apply { this.bitmap = convertImageToBitmap(imageView) }

        fun showInGallery(showInGallery: Boolean) = apply { this.showInGallery = showInGallery }

        fun build() = SaveImageInGalleryHelper(context, bitmap, showInGallery)

        private fun convertImageToBitmap(image: ImageView): Bitmap {
            return (image.drawable as BitmapDrawable).bitmap
                ?: throw IOException("Error converting drawable to bitmap.")
        }
    }
}
