////////////////////////////////////////////////////////////////////////////////////////////////////////
//FileName: SettingsActivity.kt
//FileType: Kotlin class
//Author : Reem Alnashrey
//Copy Rights : CSC2033 (Team 28)
//Description : Class for app settings
////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.example.rubbishapp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity(),
    SharedPreferences.OnSharedPreferenceChangeListener {


    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .commit()
        }



        val toolbar: Toolbar? = findViewById(R.id.Settings_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)

        // show the back button the the menu bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // nav menu
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.settings_toolbar, menu)
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

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val darkModeString = getString(R.string.dark_mode)

        if (key == darkModeString) {

            val pref = sharedPreferences?.getString(key, "1")

            when (pref?.toInt()) {
                1 -> {
                    AppCompatDelegate
                        .setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        )
                }

                2 -> AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                3 -> AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                4 -> AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    )
            }
        }


    }



    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

}