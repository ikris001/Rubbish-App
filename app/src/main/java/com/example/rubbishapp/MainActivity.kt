package com.example.rubbishapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    fun openCloseNavigationDrawer(view: View) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)


        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navView.setNavigationItemSelectedListener {

            when(it.itemId){


                R.id.nav_home -> Toast.makeText(applicationContext,"Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.nav_login -> Toast.makeText(applicationContext,"Clicked Login", Toast.LENGTH_SHORT).show()
                R.id.nav_report -> Toast.makeText(applicationContext,"Clicked Report", Toast.LENGTH_SHORT).show()
                R.id.nav_rate_review -> Toast.makeText(applicationContext,"Clicked Rate review",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_feedback -> Toast.makeText(applicationContext,"Clicked Feedback", Toast.LENGTH_SHORT).show()
                R.id.nav_settings-> Toast.makeText(applicationContext,"Clicked Settings", Toast.LENGTH_SHORT).show()
                R.id.nav_account -> Toast.makeText(applicationContext,"Clicked Account", Toast.LENGTH_SHORT).show()
                R.id.nav_contact_support -> Toast.makeText(applicationContext,"Clicked Contact support",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_Term-> Toast.makeText(applicationContext,"Clicked Term and Conditions",
                    Toast.LENGTH_SHORT).show()


            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}