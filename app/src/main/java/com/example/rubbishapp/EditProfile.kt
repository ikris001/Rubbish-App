package com.example.rubbishapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EditProfile : AppCompatActivity() {
    lateinit var auth:FirebaseAuth

    lateinit var newUsername: TextInputEditText
    lateinit var newBio: TextInputEditText
    lateinit var submit: Button
    lateinit var cancel: Button
    lateinit var Delete: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Initializations()
        auth = FirebaseAuth.getInstance()

        // edit profile when changes have been submitted
        submit.setOnClickListener {
            editProfile()
        }
    }

    // ID's called from activity

    fun Initializations() {

        newUsername = findViewById(R.id.newUsernameField)
        newBio = findViewById(R.id.newBioField)
        submit = findViewById(R.id.submitBtn)
        cancel = findViewById(R.id.cancelBtn)
        Delete = findViewById(R.id.DeleteBtn)

    }

    // Checking if the input in form is valid


    // function to edit profile username
    fun editProfile() {
        auth.currentUser?.let { user->
            val username = newUsername.text.toString()
            val updates  = UserProfileChangeRequest.Builder().setDisplayName(username).build()

            CoroutineScope(Dispatchers.IO).launch {
                try{
                    user.updateProfile(updates).await()
                    Toast.makeText(this@EditProfile,"User profile successfully updated",
                        Toast.LENGTH_LONG).show()

                } catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@EditProfile, e.message,
                            Toast.LENGTH_LONG).show()
                    }
                }
            }



        }


    }





}