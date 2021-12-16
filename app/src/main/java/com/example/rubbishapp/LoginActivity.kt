package com.example.rubbishapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*



class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // created temporary username and password for user to log in
        enterButton.setOnClickListener {
            var status =  if (username.text.toString().equals("Rubbish") &&
            password.text.toString().equals("Rubbish")) "Logged in Successfully" else "Log in fail"
        Toast.makeText(this, "status", Toast.LENGTH_SHORT).show()

    }
        // switch between activities register and login
        registerHereButton.setOnClickListener { val intent = Intent(this, RegisterFromLoginActivity::class.java)
            startActivity(intent)
        }



    }
}