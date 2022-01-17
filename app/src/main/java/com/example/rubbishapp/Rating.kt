package com.example.rubbishapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_rating.*


class Rating : AppCompatActivity() {









    fun SubmitRating(view: View) {



        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val rating = rBar.rating.toInt()

        FirebaseDatabase.getInstance().getReference("Ratings").child(id).setValue(rating)

        Toast.makeText(this@Rating,
            "Rating Submitted Successfully",
            Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, MapsActivity::class.java))

    }



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