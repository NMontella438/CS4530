package com.nmontella.app1

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class DisplayActivity : AppCompatActivity() {
    private var fullName: String? = null

    private var  picView: ImageView? = null
    private var tvFullName: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val bundle = intent.extras
        val picture = bundle!!.getByteArray("image_arr")
        fullName = bundle.getString("full_name")

        tvFullName = findViewById(R.id.da_name)
        picView = findViewById(R.id.da_imageView)

        tvFullName!!.text = fullName.plus(" ").plus(getString(R.string.login))
        val bitMap = BitmapFactory.decodeByteArray(picture, 0, picture!!.size)
        picView!!.setImageBitmap(bitMap)
    }
}