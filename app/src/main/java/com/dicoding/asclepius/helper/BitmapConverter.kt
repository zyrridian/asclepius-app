package com.dicoding.asclepius.helper

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream

object ImageConverter {

    // Convert image URI to ByteArray
    fun uriToByteArray(contentResolver: ContentResolver, uri: Uri?): ByteArray? {
        return try {
            val inputStream: InputStream? = uri?.let { contentResolver.openInputStream(it) }
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmapToByteArray(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Convert ByteArray back to Bitmap
    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    // Convert Bitmap to ByteArray
    private fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream) // Choose format and quality
        return stream.toByteArray()
    }
}