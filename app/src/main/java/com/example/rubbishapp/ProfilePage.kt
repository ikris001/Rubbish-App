package com.example.rubbishapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ProfilePage : AppCompatActivity() {

    private val editButton = findViewById<Button>(R.id.editBtn)
    private val user = "Not Current User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        editButton.isEnabled = true
        // hide and disable to edit profile button if the user is viewing another user's profile
        if (user == "Not Current User"){
            editButton.setVisibility(View.GONE)
            editButton.isEnabled = false
        }


    }
}