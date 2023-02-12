package com.nmontella.app1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Data Variables
    private var fullName: String? = null
    private var firstName: String? = null
    private var midName: String? = null
    private var lastName: String? = null
    private var nameSubmit: Boolean? = false

    //UI Variables
    private var buttonSubmit: Button? = null
    private var buttonClear: Button? = null
    private var buttonPic: Button? = null
    private var etFirstName: EditText? = null
    private var etMidName: EditText? = null
    private var etLastName: EditText? = null
    private var picView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFirstName = findViewById(R.id.et_firstName)
        etMidName = findViewById(R.id.et_middleName)
        etLastName = findViewById(R.id.et_lastName)

        buttonSubmit = findViewById(R.id.button_submit)
        buttonSubmit!!.setOnClickListener(this)
        buttonClear = findViewById(R.id.button_clear)
        buttonClear!!.setOnClickListener(this)
        buttonPic = findViewById(R.id.button_picture)
        buttonPic!!.setOnClickListener(this)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.button_clear -> {
                etFirstName!!.setText("")
                etMidName!!.setText("")
                etLastName!!.setText("")
            }
            R.id.button_submit -> {

                firstName = etFirstName!!.text.toString()
                midName = etMidName!!.text.toString()
                lastName = etLastName!!.text.toString()

                if(firstName.isNullOrBlank() || lastName.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "Enter a First and Last Name", Toast.LENGTH_SHORT).show()
                }

                fullName = firstName.plus(" ").plus(midName).plus(" ").plus(lastName)
                nameSubmit = true
            }
            R.id.button_picture -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try{
                    cameraActivity.launch(cameraIntent)
                }catch(ex: ActivityNotFoundException){
                    //Do error handling here
                }
            }
        }
    }
    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == RESULT_OK) {
            picView = findViewById<View>(R.id.pictureView) as ImageView
            //val extras = result.data!!.extras
            //val thumbnailImage = extras!!["data"] as Bitmap?

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                picView!!.setImageBitmap(thumbnailImage)
            } else {
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                picView!!.setImageBitmap(thumbnailImage)
            }
        }
    }
}