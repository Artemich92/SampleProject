package com.sampleproject.utils.helpers

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink

private const val DEFAULT_BUFFER_SIZE = 2048

class GetImageFromGalleryHelper private constructor(
    private var context: Context,
    private var uri: Uri
) {

    private fun uriToFile(): File? {
        val resolver = context.contentResolver ?: return null
        val parcelFileDescriptor = resolver.openFileDescriptor(uri,/* mode */ "r",/* cancellationSignal */ null) ?: return null
        val fileName = resolver.getFileName(uri)
        val file = File(context.filesDir, fileName)
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }

    data class AttachmentRequest(
        private val file: File,
        private val contentType: String
    ) : RequestBody() {
        override fun contentType() = "$contentType/*".toMediaTypeOrNull()

        override fun contentLength() = file.length()

        override fun writeTo(sink: BufferedSink) {
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            val fileInputStream = FileInputStream(file)
            var uploaded = 0L
            fileInputStream.use { inputStream ->
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    uploaded += read
                    sink.write(buffer, 0, read)
                }
            }
        }
    }

    class PhotoBuilder {
        fun buildUriToFile(context: Context, uri: Uri) = GetImageFromGalleryHelper(context, uri).uriToFile()
        fun buildFileToRequestBody(file: File, contentType: String) = AttachmentRequest(file, contentType)
    }

    enum class PhotoTypes(val type: String) {
        AVATAR(type = "avatar"), ARTICLE(type = "article"), FEED(type = "feed"), GALLERY(type = "gallery"), PROCEDURES(type = "skin_procedure")
    }

    private fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(
            /* uri */ fileUri,
            /* projection */ null,
            /* selection */ null,
            /* selectionArgs */ null,
            /* sortOrder */ null
        )
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }
}
