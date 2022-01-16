package com.example.rubbishapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_profile.*

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

        submitBtn.setOnClickListener {
            val newUsername: String = newUsernameField.text.toString()
            val newBio: String = newBioField.text.toString()

            if (newUsername.trim().isNotEmpty()){
                // set new username to user
                changeUsername(newUsername)
            }
            if (newBio.trim().isNotEmpty()){
                // set new bio to user
                changeDescription(newBio)
            }
            val intent = Intent(this@EditProfile, ProfilePage::class.java)
            startActivity(intent)
        }
    }

    private fun changeUsername(NewUsername: String){
        val database = FirebaseDatabase.getInstance().getReference("Users")
        val user = Firebase.auth.currentUser
        val userId = user!!.uid
        database.child("$userId/username").setValue(NewUsername)
    }

    private fun changeDescription(NewBio: String) {
        val database = FirebaseDatabase.getInstance().getReference("Users")
        val user = Firebase.auth.currentUser
        val userId = user!!.uid
        database.child("$userId/bio").setValue(NewBio)
    }

    fun changeProfilePicture(){}

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