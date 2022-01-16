package com.example.rubbishapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfile : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private var deleteAccountFlag = false

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

        auth = FirebaseAuth.getInstance()
        deleteAccountFlag = false

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

        cancelBtn.setOnClickListener {
            newUsernameField.text!!.clear()
            newBioField.text!!.clear()
        }

        deleteBtn.setOnClickListener {
            if (deleteAccountFlag) {
                // gets the current user's ID
                val userID = auth.currentUser?.uid

                // deletes the user's profile pic from the DB
                FirebaseStorage.getInstance().reference.child("images/$userID.png").delete()

                // deletes the user from the realtime DB
                FirebaseDatabase.getInstance().getReference("Users/$userID").removeValue()

                // deletes the user from the authentication DB
                auth.currentUser?.delete()?.addOnSuccessListener {
                    Toast.makeText(this, "User Deleted", Toast.LENGTH_LONG).show()
                }
                // sends the user back to the Login page
                val intent = Intent(this@EditProfile, LoginActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(
                    this,
                    "Are you sure you want to delete you're account?",
                    Toast.LENGTH_LONG
                ).show()
                deleteAccountFlag = true
            }
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