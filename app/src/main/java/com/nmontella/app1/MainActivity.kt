package com.nmontella.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Data Variables
    private var fullName: String? = null
    private var firstName: String? = null
    private var midName: String? = null
    private var lastName: String? = null

    //UI Variables
    private var buttonSubmit: Button? = null
    private var buttonClear: Button? = null
    private var etFirstName: EditText? = null
    private var etMidName: EditText? = null
    private var etLastName: EditText? = null
    private var tvDisplay: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tv_display)
        etFirstName = findViewById(R.id.et_firstName)
        etMidName = findViewById(R.id.et_middleName)
        etLastName = findViewById(R.id.et_lastName)

        buttonSubmit = findViewById(R.id.button_submit)
        buttonSubmit!!.setOnClickListener(this)
        buttonClear = findViewById(R.id.button_clear)
        buttonClear!!.setOnClickListener(this)

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
                fullName = firstName.plus(" ").plus(midName).plus(" ").plus(lastName)
                tvDisplay!!.text = fullName
            }
        }
    }
}