package com.example.rubbishapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_edit_profile.*

class editProfile : AppCompatActivity() {


    var profileImg3: ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        profileImg3 = findViewById(R.id.profileImg3)

        val editProfile = findViewById<Button>(R.id.editPictureBtn)

        val toolbar: Toolbar = findViewById(R.id.EditProfile_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // allow user to use the camera to take a picture

        editPictureBtn.setOnClickListener {
            ImagePicker.with(this).cameraOnly().crop().maxResultSize(400,400).start()
        }

        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)

        // show the back button the the menu bar
        actionBar?.setDisplayHomeAsUpEnabled(true)
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