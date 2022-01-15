package com.example.rubbishapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_edit_profile.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_maps.*


class EditProfile : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    fun openCloseNavigationDrawer3(view: View) {
        if (drawerLayout3.isDrawerOpen(GravityCompat.START)) {
            drawerLayout3.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout3.openDrawer(GravityCompat.START)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
//
//        val toolbar: Toolbar = findViewById(R.id.EditProfile_toolbar)
//        setSupportActionBar(toolbar)
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

        // navigation bar button code


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout3)
        val navView: NavigationView = findViewById(R.id.nav_view3)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navView.setNavigationItemSelectedListener {

            when (it.itemId) {


                R.id.nav_home -> {
                    startActivity(Intent(this, MapsActivity::class.java))
                }
                R.id.nav_login -> Toast.makeText(
                    applicationContext,
                    "Clicked Login",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_report -> {
                    startActivity(Intent(this, ReportActivity::class.java))
                }
                R.id.nav_rate_review -> Toast.makeText(
                    applicationContext, "Clicked Rate review",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_feedback -> startActivity(Intent(this, feedback::class.java))


                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))

                }



                R.id.nav_account -> {
                    startActivity(Intent(this, ProfilePage::class.java))
                }
                R.id.nav_contact_support -> startActivity(Intent(this, ContactSupport::class.java))
                R.id.nav_Term -> {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/terms-and-conditions-rubbish-/home"))
                    startActivity(browserIntent)
                }


            }
            true
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