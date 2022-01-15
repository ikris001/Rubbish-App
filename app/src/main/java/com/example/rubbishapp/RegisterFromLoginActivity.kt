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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_from_login.*

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


        // Register User

        enterButtonRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterFromLoginActivity, "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(passwordRegister.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterFromLoginActivity, "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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


//        enterButtonRegister.setOnClickListener {
//            if (passwordRegister.text.toString() == confirmPasswordRegister.text.toString()) {
//                val user = User(9, usernameRegister.text.toString(), passwordRegister.text.toString(),
//                email.text.toString(),"","","User",0)
//                createUserInDB(user, usersTable)
//            }
//        }


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

//    private fun createUserInDB(user: User, database: DatabaseReference) {
//        // connects to the DB and creates/overrides the user with the same user.username
//        database.child(user.username).setValue(user).addOnSuccessListener {
//            Toast.makeText(this,"Woo-hoo It's Done :)", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            Toast.makeText(this,"Oh No It's not done :(",Toast.LENGTH_SHORT).show()
//        }
//    }

}