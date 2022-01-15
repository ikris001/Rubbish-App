package com.example.rubbishapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_edit_profile.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class EditProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val toolbar: Toolbar = findViewById(R.id.EditProfile_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar


        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)

        // show the back button the the menu bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        submitBtn.setOnClickListener(){
            var newUsername: String = newUsernameField.text.toString()
            var newBio: String = newBioField.text.toString()

            if (!newUsername.trim().isEmpty()){
                // set new username to user
                changeUsername(newUsername)
            }
            if (!newBio.trim().isEmpty()){
                // set new bio to user
                changeDescription(newBio)
            }
        }

    }

    fun changeUsername(NewUsername: String){
        var database = FirebaseDatabase.getInstance().getReference("Users")
        val user = Firebase.auth.currentUser
        val userId = user!!.uid
        database.child("Users").child(userId).child("username")
            .setValue(NewUsername)
    }

    fun changeDescription(NewBio: String) {
        var database = FirebaseDatabase.getInstance().getReference("Users")
        val user = Firebase.auth.currentUser
        val userId = user!!.uid
        database.child("Users").child(userId).child("bio").setValue(NewBio)
    }

    fun changeProfilePicture(){

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.edit_profile_toolbar, menu)
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