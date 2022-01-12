package com.example.rubbishapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.rubbishapp.databinding.ActivityFeedbackBinding
import com.example.rubbishapp.databinding.ActivityMainBinding

class feedback : AppCompatActivity() {


    lateinit var binding : ActivityFeedbackBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)


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