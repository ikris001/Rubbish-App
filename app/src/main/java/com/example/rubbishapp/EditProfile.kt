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

/**
 *  edit users profile with firebase auth
 */

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
        initializations()
        auth = FirebaseAuth.getInstance()

        // edit profile when changes have been submitted
        submit.setOnClickListener {
            editProfile()
            editBio()
        }
    }

    override fun onStart() {
        super.onStart()
        validateAccount()
    }

    // ID's called from activity

    private fun initializations() {

        newUsername = findViewById(R.id.newUsernameField)
        newBio = findViewById(R.id.newBioField)
        submit = findViewById(R.id.submitBtn)
        cancel = findViewById(R.id.cancelBtn)
        Delete = findViewById(R.id.DeleteBtn)

    }

    // Checking if the input in form is valid


    // function to edit profile username
    private fun editProfile() {
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

    // Function to update BIO
    private fun editBio() {
        auth.currentUser?.let { user ->
            val bio = newBio.text.toString()
            val updateBio = UserProfileChangeRequest.Builder().setDisplayName(bio).build()
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    user.updateProfile(updateBio).await()
                    Toast.makeText(this@EditProfile,"User Bio successfully updated",
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

    // check to see if user is logged in or not
    private fun validateAccount(){
        val user = auth.currentUser
        if (user == null){
            // todo user not currently logged in
        } else{
            newUsername.setText(user.displayName)
            newBio.setText(user.displayName)
        }

    }
    /***
     *  update profile activities without firebase authentication
     */

    // Check if the input in form is valid
    fun authInput(): Boolean {
        if (newUsername.text.toString().equals("")) {
            newUsername.setError("Please Enter First Name")
            return false
        }
        if (newBio.text.toString().equals("")) {
            newBio.setError("Please Enter Last Name")
            return false
        }
        return true
    }

    fun performEditProfile (view: View) {
        if (authInput()) {

            // configure database to check user validity

            val username= newUsername.text.toString()
            val bio = newBio.text.toString()

            Toast.makeText(this,"Profile Update Successfully",Toast.LENGTH_SHORT).show()
        }
    }
}