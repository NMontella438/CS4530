package com.nmontella.app1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Data Variables
    private var fullName: String? = null
    private var firstName: String? = null
    private var midName: String? = null
    private var lastName: String? = null
    private var profilePic: Bitmap? = null

    //UI Variables
    private var buttonSubmit: Button? = null
    private var buttonClear: Button? = null
    private var buttonPic: Button? = null
    private var etFirstName: EditText? = null
    private var etMidName: EditText? = null
    private var etLastName: EditText? = null
    private var picView: ImageView? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null){
            firstName = savedInstanceState.getString("first_name")
            midName = savedInstanceState.getString("mid_name")
            lastName = savedInstanceState.getString("last_name")
            profilePic = savedInstanceState.getParcelable("profile_pic", Bitmap::class.java)
        }
        setContentView(R.layout.activity_main)

        etFirstName = findViewById(R.id.et_firstName)
        etMidName = findViewById(R.id.et_middleName)
        etLastName = findViewById(R.id.et_lastName)
        picView = findViewById(R.id.pictureView)

        buttonSubmit = findViewById(R.id.button_submit)
        buttonSubmit!!.setOnClickListener(this)
        buttonClear = findViewById(R.id.button_clear)
        buttonClear!!.setOnClickListener(this)
        buttonPic = findViewById(R.id.button_picture)
        buttonPic!!.setOnClickListener(this)

        if(profilePic != null)
            picView!!.setImageBitmap(profilePic)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        firstName = savedInstanceState.getString("first_name")
        midName = savedInstanceState.getString("mid_name")
        lastName = savedInstanceState.getString("last_name")
        profilePic = savedInstanceState.getParcelable("profile_pic", Bitmap::class.java)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("first_name", firstName)
        outState.putString("mid_name", midName)
        outState.putString("last_name", lastName)
        outState.putParcelable("profile_pic", profilePic)
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
                    Toast.makeText(this@MainActivity, "Enter a first and last name", Toast.LENGTH_SHORT).show()
                }
                else if(firstName!!.split("\\s+".toRegex()).toTypedArray().size != 1){
                    Toast.makeText(this@MainActivity, "Enter only one first name", Toast.LENGTH_SHORT).show()
                }
                else if(lastName!!.split("\\s+".toRegex()).toTypedArray().size != 1){
                    Toast.makeText(this@MainActivity, "Enter only one last name", Toast.LENGTH_SHORT).show()
                }
                else if(profilePic == null){
                    Toast.makeText(this@MainActivity, "Take profile picture before submitting", Toast.LENGTH_SHORT).show()
                }
                else {
                    fullName = firstName.plus(" ").plus(lastName)

                    val displayIntent = Intent(this@MainActivity, DisplayActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("full_name", fullName)
                    displayIntent.putExtra("profile_pic", profilePic)
                    displayIntent.putExtras(bundle)
                    startActivity(displayIntent)
                }
            }
            R.id.button_picture -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try{
                    cameraActivity.launch(cameraIntent)
                }catch(ex: ActivityNotFoundException){
                    Toast.makeText(this@MainActivity, "Camera Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == RESULT_OK) {
            picView = findViewById<View>(R.id.pictureView) as ImageView

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                picView!!.setImageBitmap(thumbnailImage)
                profilePic = thumbnailImage
            } else {
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                picView!!.setImageBitmap(thumbnailImage)
                profilePic = thumbnailImage
            }
        }
    }
}