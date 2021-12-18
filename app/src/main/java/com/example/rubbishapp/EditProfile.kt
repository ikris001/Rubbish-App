package com.example.rubbishapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class EditProfile : AppCompatActivity() {


    lateinit var newUsername: TextInputEditText
    lateinit var newBio: TextInputEditText
    lateinit var submit: Button
    lateinit var cancel: Button
    lateinit var Delete: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Initializations()
    }

    // ID's called from activity

    fun Initializations() {

        newUsername = findViewById(R.id.newUsernameField)
        newBio = findViewById(R.id.newBioField)
        submit = findViewById(R.id.submitBtn)
        cancel = findViewById(R.id.cancelBtn)
        Delete = findViewById(R.id.DeleteBtn)

    }

    // Checking if the input in form is valid

    fun validateInput(): Boolean{
        if (newUsername.text.toString().equals(""))
            newUsername.setError("Please enter your new username")
            return false
    }

    fun performEditProfile (view: View) {
        if(validateInput())

            //var newUsername = newUsername.text.toString()

            Toast.makeText(this,"Profile Update Successfully",Toast.LENGTH_SHORT).show()
    }





}