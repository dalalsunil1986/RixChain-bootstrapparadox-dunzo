package com.dunzo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import java.io.File
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.*
import kotlinx.android.synthetic.main.activity_review_receipt.*
import uk.co.senab.photoview.PhotoViewAttacher


class ReviewReceiptActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    var categories = arrayOf("Groceries", "Electronics", "Food")

    var spinner: Spinner? = null
    lateinit var mPhotoViewAttacher: PhotoViewAttacher
    companion object;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_receipt)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        spinner = this.spinner_category
        spinner!!.onItemSelectedListener = this

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa


        val receiptPath = intent.getStringExtra(MainActivity.KEY_RECEIPT_PATH)
//        var receipt = AppUtils.getBitmap(receiptPath, this)
        val mReceiptFile = File(receiptPath)
        var receipt: Bitmap? = null
        if (mReceiptFile.exists()) {
            receipt = BitmapFactory.decodeFile(mReceiptFile.absolutePath)
        }

        val review = findViewById<Button>(R.id.viewData)
        review.setOnClickListener{

            val intent = Intent(this, Tab::class.java)



            startActivity(intent)




        }

        val ivReceipt = findViewById<ImageView>(R.id.review_receipt_iv_receipt)
        val vto = ivReceipt.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                ivReceipt.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = ivReceipt.measuredWidth
                val height = ivReceipt.measuredHeight
                var newWidth = receipt!!.width
                if (receipt!!.width < width) {
                    newWidth = width
                }

                val newHeight = newWidth * height / width
                val newReceipt = Bitmap.createScaledBitmap(receipt, newWidth, newHeight, false)

                ivReceipt.setImageBitmap(newReceipt)
                mPhotoViewAttacher = PhotoViewAttacher(ivReceipt)
                mPhotoViewAttacher.scaleType = ImageView.ScaleType.CENTER_CROP

            }
        })

    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        println("Selected : "+categories[position])
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

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