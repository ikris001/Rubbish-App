////////////////////////////////////////////////////////////////////////////////////////////////////////
//FileName: ContactSupport.kt
//FileType: Kotlin class
//Author : Reem Alnashrey
//Copy Rights : CSC2033 (Team 28)
//Description : Class to show our contact details
////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.example.rubbishapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar


class ContactSupport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_support)

        val toolbar: Toolbar = findViewById(R.id.Contact_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)

        // show the back button the the menu bar
        actionBar?.setDisplayHomeAsUpEnabled(true)


    }
}