package com.example.rubbishapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_contact_support.*
import kotlinx.android.synthetic.main.activity_maps.*

class ContactSupport : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    fun openCloseNavigationDrawer_contact(view: View) {
        val nv_email = findViewById<View>(R.id.nav_header_user_email) as TextView
        nv_email.text = FirebaseAuth.getInstance()
            .currentUser!!.email


        val nv_name = findViewById<View>(R.id.nav_header_user_name) as TextView
        nv_name.text = FirebaseAuth.getInstance()
            .currentUser!!.displayName

        if (drawerLayout_contact_support.isDrawerOpen(GravityCompat.START)) {
            drawerLayout_contact_support.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout_contact_support.openDrawer(GravityCompat.START)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_support)



        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout_contact_support)
        val navView: NavigationView = findViewById(R.id.nav_view_contact_support)


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
                R.id.nav_rate_review ->{
                    startActivity(Intent(this, Rating::class.java))
                }


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
}