package com.sampleproject.utils.helpers

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.sampleproject.BuildConfig
import com.sampleproject.utils.helpers.ShareImageInSocialHelper.Socials.GENERAL
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

private const val MIME_TYPE_IMAGE = "image/*"
private const val TO_SHARE_TITLE = "ToShare"
private const val ENDING_IMAGE_PATH = "cosmetology.png"
private const val PROVIDER = ".provider"

class ShareImageInSocialHelper private constructor(
    private var context: Context?,
    private var bitmap: Bitmap,
    private var nameSocial: Socials
) {

    private val scopeShareImage = CoroutineScope(Dispatchers.IO)

    init {
        scopeShareImage.launch {
            shareImage()
        }
    }

    private fun shareImage() {
        val shareIntent = Intent()
        shareIntent.apply {
            action = Intent.ACTION_SEND
            type = MIME_TYPE_IMAGE
            when (nameSocial) {
                GENERAL -> {
                    uriPermission(Intent.createChooser(shareIntent, TO_SHARE_TITLE))
                    shareImageIntent(shareIntent)
                }
                else -> {
                    setPackage(nameSocial.value)
                    shareImageIntent(shareIntent)
                }
            }
        }
        scopeShareImage.cancel()
        context = null
    }

    private fun shareImageIntent(shareIntent: Intent) {
        try {
            shareIntent.putExtra(Intent.EXTRA_STREAM, getUri())
            context?.startActivity(Intent.createChooser(shareIntent, TO_SHARE_TITLE))
        } catch (exception: Exception) {
            Toast.makeText(context, "Failed to share content", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uriPermission(chooser: Intent) {
        val resInfoList: List<ResolveInfo> =
            context?.packageManager!!.queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            context?.grantUriPermission(
                resolveInfo.activityInfo.packageName, getUri(),
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }

    private fun getUri(): Uri {
        val path = context?.externalCacheDir.toString() + File.separator + ENDING_IMAGE_PATH
        val file = File(path)
        file.delete()
        val fileOutputStream = FileOutputStream(path)
        bitmap.compress(Bitmap.CompressFormat.PNG, MAX_QUALITY, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        return context?.let {
            FileProvider.getUriForFile(
                it,
                BuildConfig.APPLICATION_ID + PROVIDER,
                file
            )
        } ?: throw NullPointerException("Context cannot be equal null")
    }

    class Builder {
        private lateinit var context: Context
        private lateinit var bitmap: Bitmap
        private lateinit var nameSocial: Socials

        fun with(context: Context) = apply { this.context = context }

        fun from(imageView: ImageView) = apply { this.bitmap = convertImageToBitmap(imageView) }

        fun setSocialNetwork(nameSocial: Socials) = apply { this.nameSocial = nameSocial }

        fun build() = ShareImageInSocialHelper(context, bitmap, nameSocial)

        private fun convertImageToBitmap(image: ImageView): Bitmap {
            return (image.drawable as BitmapDrawable).bitmap ?: throw IOException("Error converting drawable to bitmap.")
        }
    }

    enum class Socials(val value: String) {
        GENERAL(""),
        VKONTAKTE("com.vkontakte.android"),
        TELEGRAM("org.telegram.messenger"),
        WHATSAPP("com.whatsapp"),
        VIBER("com.viber.voip"),
        FACEBOOK("com.facebook.katana"),
        INSTAGRAM("com.instagram.android")
    }
}
