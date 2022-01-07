package com.example.rubbishapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button


class ProfilePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        // take user to a different activity when edit profile is clicked
        // for user data to be updated
        val editButton = findViewById<Button>(R.id.editButton)

        editButton.setOnClickListener {
            val intentProfile = Intent(this, EditProfile::class.java)
            startActivity(intentProfile)
        }







    }
}