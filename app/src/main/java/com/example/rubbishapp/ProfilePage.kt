package com.example.rubbishapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.settings_activity.view.*


class ProfilePage : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        val toolbar: Toolbar = findViewById(R.id.Profile_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar


        // switch to edit profile
        editButton.setOnClickListener {
            val intent = Intent(this,editProfile::class.java)
            startActivity(intent)
            finish()
        }

        // Is the user logged in?
        auth = FirebaseAuth.getInstance()

        val database = FirebaseDatabase.getInstance()
        val path: String = "Users/" + auth.currentUser?.uid.toString()
        val ref: DatabaseReference = database.getReference(path)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: User? = snapshot.getValue(User::class.java)
                    val bio = findViewById<TextView>(R.id.bioTxt)
                    bio.text = user?.bio
                    usernameTxt.text = user?.username

                    changeProfileInformation(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })


        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.profile_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun changeProfileInformation(user: User?) {
        headerTxt.text = user?.username
        usernameTxt.text = user?.username
        bioTxt.text = user?.bio
        scoreTxt.text = user?.score.toString()
    }

}