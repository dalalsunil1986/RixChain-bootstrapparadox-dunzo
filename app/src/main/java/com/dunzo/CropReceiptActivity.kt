package com.dunzo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import org.opencv.android.OpenCVLoader
import java.io.IOException


class CropReceiptActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        if(v!!.id == R.id.scanButton){
            sourceFrame!!.post{
                val croppedBitmap = OpenCVUtils.cropReceiptByFourPoints(bitmap!!, polygonView!!.getListPoint(), sourceImageView.width, sourceImageView.height)
                val savedPath = AppUtils.saveBitmapToFile(croppedBitmap!!)
                val intent = Intent(this, ReviewReceiptActivity::class.java)
                intent.putExtra(MainActivity.KEY_RECEIPT_PATH, savedPath)
                startActivity(intent)
            }
        }
    }

    init {
        if (!OpenCVLoader.initDebug()) {
            Log.e("Scan", "OpenCVLoader.initDebug() = FALSE")
        } else {
            Log.e("Scan", "OpenCVLoader.initDebug() = TRUE")
        }
    }

    private var scanButton: Button? = null
    private lateinit var sourceImageView: ImageView
    private var sourceFrame: FrameLayout? = null
    private var polygonView: PolygonView? = null

    private var original: Bitmap? = null

    private var bitmap: Bitmap?
        get() {
            val uri = uri
            try {
                var bitmap = AppUtils.getBitmap(uri, this)
                bitmap.density = Bitmap.DENSITY_NONE
                if (bitmap.width > bitmap.height) {
                    bitmap = OpenCVUtils.rotate(bitmap, 90)
                }
                return bitmap
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }
        set(original) {
            val scaledBitmap = Bitmap.createScaledBitmap(original, sourceImageView.width, sourceImageView.height, false)
            sourceImageView.setImageBitmap(scaledBitmap)
            val pointFs = OpenCVUtils.getEdgePoints(scaledBitmap, polygonView!!)
            polygonView!!.points = pointFs!!
            polygonView!!.visibility = View.VISIBLE

            val layoutParams = FrameLayout.LayoutParams(sourceImageView.width, sourceImageView.height)
            layoutParams.gravity = Gravity.CENTER
            polygonView!!.layoutParams = layoutParams
        }

    private val uri: Uri?
        get() = intent.getParcelableExtra(MainActivity.KEY_RECEIPT_PATH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop_receipt)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        init()
    }

    private fun init() {
        sourceImageView = findViewById(R.id.sourceImageView)
        scanButton = findViewById(R.id.scanButton)
        scanButton!!.setOnClickListener(this)
        sourceFrame = findViewById(R.id.sourceFrame)
        polygonView = findViewById(R.id.polygonView)
        sourceFrame!!.post {
            original = bitmap
            if (original != null) {
                bitmap = original
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}