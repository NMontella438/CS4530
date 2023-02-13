package com.nmontella.app1

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.lang.reflect.TypeVariable

class DisplayActivity : AppCompatActivity(), View.OnClickListener {
    private var fullName: String? = null
    private var profilePic: Bitmap? = null
    private var  picView: ImageView? = null
    private var tvFullName: TextView? = null
    private var restartButton: Button? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val bundle = intent.extras
        profilePic = intent.getParcelableExtra("profile_pic", Bitmap::class.java)
        fullName = bundle!!.getString("full_name")

        tvFullName = findViewById(R.id.da_name)
        picView = findViewById(R.id.da_imageView)

        tvFullName!!.text = fullName.plus(" ").plus(getString(R.string.login))
        picView!!.setImageBitmap(profilePic)

        restartButton = findViewById(R.id.button_restart)
        restartButton!!.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.putString("full_name", fullName)
    }
    override fun onClick(p0: View?) {
        val mainIntent = Intent(this@DisplayActivity, MainActivity::class.java)
        startActivity(mainIntent)
    }

}