package com.dunzo

import android.Manifest
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.support.v4.content.PermissionChecker
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : Fragment(), View.OnClickListener {

    companion object {
        val KEY_RECEIPT_PATH = "RECEIPT_PATH"
        val IMAGE_PATH = Environment
                .getExternalStorageDirectory().path + "/scanSample"
    }

    private val REQUEST_PERMISSION_CAMERA = 101
    private val REQUEST_RECEIPT_CAPTURE = 100
    private val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 102
    private val REQUEST_PICK_IMAGE = 103
    lateinit var mTvCaptureReceipt: TextView
    lateinit var mTvChooseGallery: TextView
    lateinit var mCurrentPhotoPath: String
    private var fileUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.activity_main, container, false)


        initViews()

        return view
    }


    private fun initViews() {
        mTvCaptureReceipt = view.findViewById(R.id.tv_capture_receipt)
        mTvChooseGallery = view.findViewById(R.id.tv_choose_gallery)

        mTvCaptureReceipt.setOnClickListener(this)
        mTvChooseGallery.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            mTvCaptureReceipt.id -> {
                checkCameraPermission()
            }
            mTvChooseGallery.id -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE)
            }
        }
    }

    private fun startScanCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = createImageFile()
        val isDirectoryCreated = file.parentFile.mkdirs()
        Log.d("", "openCamera: isDirectoryCreated: $isDirectoryCreated")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val tempFileUri = FileProvider.getUriForFile(activity,
                    "com.scanlibrary.provider", // As defined in Manifest
                    file)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri)
        } else {
            val tempFileUri = Uri.fromFile(file)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri)
        }
        startActivityForResult(cameraIntent, REQUEST_RECEIPT_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECEIPT_CAPTURE && resultCode == Activity.RESULT_OK) {
            startCropActivity(fileUri!!)
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data!!.getData() as Uri
            startCropActivity(imageUri)
        }
    }

    private fun checkCameraPermission() {
        if (!(PermissionChecker.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            val permissions = arrayOf(Manifest.permission.CAMERA)
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSION_CAMERA)
        } else {
            checkExternalPermission()
        }
    }

    private fun checkExternalPermission() {
        if (!(PermissionChecker.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
        } else {
            startScanCamera()
        }
    }

    fun startReviewReceiptActivity(receiptPath: Uri) {
        val intent = Intent(activity, ReviewReceiptActivity::class.java)
        intent.putExtra(KEY_RECEIPT_PATH, receiptPath)
        startActivity(intent)

    }

    fun startCropActivity(receiptPath: Uri) {
        val intent = Intent(activity, CropReceiptActivity::class.java)
        intent.putExtra(KEY_RECEIPT_PATH, receiptPath)
        startActivity(intent)
    }

    private fun createImageFile(): File {
        clearTempImages()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val file = File(IMAGE_PATH, "IMG_" + timeStamp +
                ".jpg")
        fileUri = Uri.fromFile(file)
        return file
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE -> {
                val grantedExternal = grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (grantedExternal) {
                    // process our code
                    startScanCamera()
                } else {
                }
            }
            REQUEST_PERMISSION_CAMERA -> {
                val grantedCamera = grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (grantedCamera) {
                    checkExternalPermission()
                } else {

                }
            }
            else ->
                //                mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun clearTempImages() {
        try {
            val tempFolder = File(IMAGE_PATH)
            for (f in tempFolder.listFiles())
                f.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}

