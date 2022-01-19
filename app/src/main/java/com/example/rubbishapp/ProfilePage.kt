package com.example.rubbishapp

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile_page.*
import java.io.File


class ProfilePage : AppCompatActivity() {

    lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        val toolbar: Toolbar = findViewById(R.id.Profile_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)

        // show the back button the the menu bar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        // switch to edit profile
        editButton.setOnClickListener {
            val intent = Intent(this,EditProfile::class.java)
            startActivity(intent)
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
                    changeProfileInformation(user)
                    getProfilePicFromDB()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })


        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)
    }

    /**
     * This method is used to display the toolbar at the top of the activity.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.profile_toolbar, menu)
        return true
    }

    /**
     * This method is used to handle the clicks on the toolbar.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method is usd to change the users information on the page.
     */
    fun changeProfileInformation(user: User?) {
        usernameTxt.text = user?.username
        bioTxt.text = user?.bio
        scoreTxt.text = user?.score.toString()
    }

    /**
     * This method is used to get the profile picture from the firebase database
     * and display the picture in the ImageView holder.
     */
    private fun getProfilePicFromDB() {

        val storageRef = FirebaseStorage.getInstance().reference.child("images/profilePic.png")

        val localFile = File.createTempFile("prefix","suffix")
        storageRef.getFile(localFile).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            profileImg.setImageBitmap(bitmap)

        }.addOnFailureListener {

            Toast.makeText(this, "Couldn't Find Profile Picture", Toast.LENGTH_SHORT).show()

        }

    }

}