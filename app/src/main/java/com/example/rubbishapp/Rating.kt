package com.example.rubbishapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rating.*


class Rating : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        rBar.setOnRatingBarChangeListener { p0, p1, p2 ->
            Toast.makeText(
                this@Rating,
                "Given rating is: $p1",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}