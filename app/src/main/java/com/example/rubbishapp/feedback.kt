////////////////////////////////////////////////////////////////////////////////////////////////////////
//FileName: feedback.kt
//FileType: Kotlin class
//Author : Reem Alnashrey
//Copy Rights : CSC2033 (Team 28)
//Description : Class to allow users to send us email.
////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.example.rubbishapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.rubbishapp.databinding.ActivityFeedbackBinding
import com.example.rubbishapp.databinding.ActivityMainBinding


class feedback : AppCompatActivity() {


    lateinit var binding : ActivityFeedbackBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar: Toolbar = findViewById(R.id.feedback_toolbar)
        setSupportActionBar(toolbar)
        val actionBar1 = supportActionBar

        // show the title defined in the manifest.xml file
        actionBar1?.setDisplayShowTitleEnabled(true)

        // show the back button the the menu bar
        actionBar1?.setDisplayHomeAsUpEnabled(true)


        binding.sendBtn.setOnClickListener {


            val email = binding.emailAddress.text.toString()
            val subject = binding.subject.text.toString()
            val message = binding.Message.text.toString()


            val addresses = email.split(",".toRegex()).toTypedArray()


            val intent = Intent(Intent.ACTION_SENDTO).apply {

                data = Uri.parse("mailto:")


                putExtra(Intent.EXTRA_EMAIL,addresses)
                putExtra(Intent.EXTRA_SUBJECT,subject)
                putExtra(Intent.EXTRA_TEXT,message)



            }


            if (intent.resolveActivity(packageManager) != null){


                startActivity(intent)

            }else{

                Toast.makeText(this@feedback,"Required App is not installed", Toast.LENGTH_SHORT).show()

            }



        }
    }
}