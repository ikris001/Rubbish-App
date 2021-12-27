package com.example.rubbishapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_register_from_login.*

class RegisterFromLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_from_login)

        // called loginHereButton id from activity_register_from_login.xml
        // switch between activities register and login

        loginHereButton.setOnClickListener {
            val intent1 = Intent(this, LoginActivity::class.java)
            startActivity(intent1)
        }


        val toolbar: Toolbar = findViewById(R.id.RegisterFromLogin_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.registerfromlogin_toolbar, menu)
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