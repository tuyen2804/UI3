package com.example.myapplication.Utils

import android.graphics.BitmapFactory
import java.io.File

object FileUtils {
    fun getSizeFile(path: String?): String {
        if (path == null) return "EMPTY"
        val file = File(path)
        if (!file.exists()) {
            return "EMPTY"
        }
        val fileSizeInBytes = file.length()
        return when {
            fileSizeInBytes >= 1024 * 1024 -> {
                val fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0)
                "%.2f MB".format(fileSizeInMB)
            }
            fileSizeInBytes >= 1024 -> {
                val fileSizeInKB = fileSizeInBytes / 1024.0
                "%.2f KB".format(fileSizeInKB)
            }
            else -> "$fileSizeInBytes bytes"
        }
    }

    fun getImageWidth(path: String?): Int {
        if (path == null) return 0
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(path, options)
        return options.outWidth
    }

    fun getImageHeight(path: String?): Int {
        if (path == null) return 0
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(path, options)
        return options.outHeight
    }

    fun getImageType(path: String?): String {
        if (path == null) return "UNKNOWN"
        val file = File(path)
        if (!file.exists()) {
            return "UNKNOWN"
        }
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(path, options)

        // Extracting the file extension from the MIME type
        val mimeType = options.outMimeType ?: return "UNKNOWN"
        val parts = mimeType.split("/")
        return if (parts.size == 2) parts[1] else "UNKNOWN"
    }
}
