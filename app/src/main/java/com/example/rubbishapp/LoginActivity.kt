package com.example.rubbishapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

/**
 * @author Suleiman Sabo Muhammad
 * LoginActivity class checks users details from the database to see if they have been registered
 * allowing them access if details are found
 * @see activity_register.xml
 */


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        imageView.setImageResource(R.drawable.logo)

        /**
         *  checks user email and password  to see if it is already saved on the database
         *  allowing them access to the application if
         *
         */

        enterButton.setOnClickListener {
            when {
                // checks to see if the email field is empty
                TextUtils.isEmpty(loginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity, "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // checks to see if the password field is empty
                TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity, "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = loginEmail.text.toString().trim { it <= ' ' }
                    val password = password.text.toString().trim { it <= ' ' }

                    // create an instance and login a user with email and password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult>{ task ->
                                // if the registration is successfully done

                                if (task.isSuccessful){

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "You are registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this@LoginActivity,
                                       MapsActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", FirebaseAuth.getInstance()
                                        .currentUser!!.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // if task not successful show error message
                                    Toast.makeText(this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT).show()
                                }

                            }
                        )
                }
            }
        }


        /**
         *  takes user to the register activity when clicked
         */
        registerHereButton.setOnClickListener { val intent = Intent(this, RegisterFromLoginActivity::class.java)
            startActivity(intent)
        }

        val toolbar : Toolbar = findViewById(R.id.Login_toolbar)
        setSupportActionBar(toolbar)

    }

    /**
     * This method is used to display the toolbar at the top of the activity.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.login_toolbar, menu)
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

    override fun onBackPressed() {}
}