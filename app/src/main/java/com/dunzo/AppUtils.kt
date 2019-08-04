package com.dunzo

import android.app.Activity
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AppUtils {
    companion object {
        @Throws(IOException::class)
        fun getBitmap(selectedimg: Uri?, activity: Activity): Bitmap {
            val options = BitmapFactory.Options()
            options.inSampleSize = 3
            var fileDescriptor: AssetFileDescriptor? = null
            fileDescriptor = activity.contentResolver.openAssetFileDescriptor(selectedimg!!, "r")
            return BitmapFactory.decodeFileDescriptor(
                    fileDescriptor!!.fileDescriptor, null, options)
        }

        fun saveBitmapToFile(bm: Bitmap): String {
            val file = getOutputMediaFile()
            try {
                file!!.createNewFile()
                val fos = FileOutputStream(file)
                bm.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()
            } catch (e: Exception) {
                Log.e("Error", e.message)
            }

            return file!!.getAbsolutePath()
        }

        fun getOutputMediaFile(): File? {

            val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MyCameraApp")

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory")
                    return null
                }
            }
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val mediaFile: File
            mediaFile = File(mediaStorageDir.path + File.separator +
                    "IMG_" + timeStamp + ".jpg")
            return mediaFile
        }
    }


}