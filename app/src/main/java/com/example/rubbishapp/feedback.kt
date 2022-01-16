package com.example.rubbishapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.rubbishapp.databinding.ActivityFeedbackBinding
import com.example.rubbishapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.activity_maps.*

class feedback : AppCompatActivity() {


    lateinit var binding: ActivityFeedbackBinding
    lateinit var toggle: ActionBarDrawerToggle

    fun openCloseNavigationDrawer_feedback(view: View) {
        val nv_email = findViewById<View>(R.id.nav_header_user_email) as TextView
        nv_email.text = FirebaseAuth.getInstance()
            .currentUser!!.email


        val nv_name = findViewById<View>(R.id.nav_header_user_name) as TextView
        nv_name.text = FirebaseAuth.getInstance()
            .currentUser!!.displayName

        if (drawerLayout_feedback.isDrawerOpen(GravityCompat.START)) {
            drawerLayout_feedback.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout_feedback.openDrawer(GravityCompat.START)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.sendBtn.setOnClickListener {


            val email = binding.emailAddress.text.toString()
            val subject = binding.subject.text.toString()
            val message = binding.Message.text.toString()


            val addresses = email.split(",".toRegex()).toTypedArray()


            val intent = Intent(Intent.ACTION_SENDTO).apply {

                data = Uri.parse("mailto:")


                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)


            }


            if (intent.resolveActivity(packageManager) != null) {


                startActivity(intent)

            } else {

                Toast.makeText(this@feedback, "Required App is not installed", Toast.LENGTH_SHORT)
                    .show()

            }
        }
            // the code for nav menu functionality

            val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout_feedback)
            val navView: NavigationView = findViewById(R.id.nav_view_feedback)


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
                    R.id.nav_rate_review -> {
                        startActivity(Intent(this, Rating::class.java))
                    }


                    R.id.nav_feedback -> startActivity(Intent(this, feedback::class.java))


                    R.id.nav_settings -> {
                        startActivity(Intent(this, SettingsActivity::class.java))

                    }


                    R.id.nav_account -> {
                        startActivity(Intent(this, ProfilePage::class.java))
                    }
                    R.id.nav_contact_support -> startActivity(
                        Intent(
                            this,
                            ContactSupport::class.java
                        )
                    )
                    R.id.nav_Term -> {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://sites.google.com/view/terms-and-conditions-rubbish-/home")
                            )
                        startActivity(browserIntent)
                    }


                }
                true
            }
    }
}


