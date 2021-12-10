package com.example.rubbishapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*





class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

    enterButton.setOnClickListener {
       var status =  if (username.text.toString().equals("Rubbish") &&
            password.text.toString().equals("Rubbis")) "Logged in Successfully" else "Log in fail"
        Toast.makeText(this, "status", Toast.LENGTH_SHORT).show()


    }

    }
}