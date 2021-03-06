package com.example.rubbishapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_from_login.*

/**
 * @author Suleiman Sabo Muhammad
 * RegisterFromLogin class that takes use data and register it on the database
 * @see activity_register_from_login.xml
 */




class RegisterFromLoginActivity : AppCompatActivity() {
    private lateinit var usernameRegister : EditText
    private lateinit var passwordRegister : EditText
    private lateinit var confirmPasswordRegister : EditText
    private lateinit var email : EditText
    private lateinit var enterButtonRegister : Button
    //private val usersTable = FirebaseDatabase.getInstance().getReference("Users")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_from_login)
        imageViewRegister.setImageResource(R.drawable.logo)

        usernameRegister = findViewById(R.id.usernameRegister)
        passwordRegister = findViewById(R.id.passwordRegister)
        confirmPasswordRegister = findViewById(R.id.confirmPasswordRegister)
        email = findViewById(R.id.email)
        enterButtonRegister = findViewById(R.id.enterButtonRegister)
        var database = FirebaseDatabase.getInstance().getReference("Users")


        /**
         *  This function is triggered when a user clicked the enter button on the register activity
         *  and some requirement checks are done before registering the user.
         *
         */


        enterButtonRegister.setOnClickListener {
            when {
                // checks to see if the email field is empty
                TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterFromLoginActivity, "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // checks to see if the password field is empty
                TextUtils.isEmpty(passwordRegister.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterFromLoginActivity, "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // checks to see if the passwords matched is empty
                !TextUtils.equals(passwordRegister.text.toString(),
                                 confirmPasswordRegister.text.toString()) -> {
                    Toast.makeText(
                        this@RegisterFromLoginActivity, "Please make sure passwords match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = email.text.toString().trim { it <= ' ' }
                    val password = passwordRegister.text.toString().trim { it <= ' ' }

                    // create an instance and register a user with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult>{ task ->
                                // if the registration is successfully done

                                if (task.isSuccessful){

                                    // firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    // make a user in the realtime database table
                                    val user = User(firebaseUser.uid, usernameRegister.text.toString(),
                                        "",bioRegister.text.toString(),"User",0)
                                    database.child(firebaseUser.uid).setValue(user)

                                    Toast.makeText(
                                        this@RegisterFromLoginActivity,
                                        "You are registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this@RegisterFromLoginActivity,
                                        LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // if task not successful show error message
                                    Toast.makeText(this@RegisterFromLoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT).show()
                                }

                            }
                        )
                }
            }
        }

        val toolbar: Toolbar = findViewById(R.id.RegisterFromLogin_toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        // show the title defined in the manifest.xml file
        actionBar?.setDisplayShowTitleEnabled(true)

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * This method is used to display the toolbar at the top of the activity.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.registerfromlogin_toolbar, menu)
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

}