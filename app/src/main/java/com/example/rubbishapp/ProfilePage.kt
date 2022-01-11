package com.example.rubbishapp

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
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.settings_activity.view.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener


class ProfilePage : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        val toolbar: Toolbar = findViewById(R.id.Profile_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // Is the user logged in?
        auth = FirebaseAuth.getInstance()
        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_LONG).show()

        val database = FirebaseDatabase.getInstance()
        val path: String = "Users/" + auth.currentUser?.uid.toString() + "/bio"
        val ref = database.getReference(path)
        ref.addValueEventListener()

        val bio = findViewById<TextView>(R.id.bioTxt)
        Toast.makeText(this, path, Toast.LENGTH_LONG).show()
        var bioFromDB = FirebaseDatabase.getInstance().getReference(path)

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

}