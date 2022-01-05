package com.example.rubbishapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.util.Patterns
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

        enterButtonRegister.setOnClickListener() {
            createAccount()
        }

    }

    fun createAccount() {
        val usernameBox:EditText = findViewById(R.id.usernameRegister)
        val usernameInput = usernameBox.text
        val emailBox:EditText = findViewById(R.id.email)
        val emailInput = emailBox.text
        val passwordBox:EditText = findViewById(R.id.passwordRegister)
        val passwordInput = passwordBox.text
        val confirmPasswordBox:EditText = findViewById(R.id.confirmPasswordRegister)
        val confirmPasswordInput = confirmPasswordBox.text

        var valid:Boolean = true

        val validUsername:Boolean = usernameValidation(usernameInput.toString())
        if (!validUsername){
            valid = false
            print("Username already Taken")
        }

        val validEmail:Boolean = emailValidation(emailInput.toString())
        if (!validEmail) {
            valid = false
            print("Email does not exist or is already being used")
        }

        val validPassword:Boolean = passwordValidation(passwordInput.toString())
        if (!validPassword) {
            valid = false
            print("Password needs to contain at least one UPPERCASE and lowercase letter," +
                    " a number and a special character")
        }

        val validConfirmPassword:Boolean = confirmPasswordValidation(confirmPasswordInput.toString()
            , passwordInput.toString())
        if (!validConfirmPassword) {
            valid = false
            print("Could not confirm password as they do not match")
        }

        if (valid){
            // TODO: query database to generate new user ID
            val newUserId:Int = 0
            var newUser = User(newUserId, usernameInput.toString(), passwordInput.toString(),
                emailInput.toString(), "profilePictureAddress", "No Bio",
                "user", 0)
            // TODO: adds new user to database
        }

        // TODO: Logs user in and redirects to main activity
    }

    fun confirmPasswordValidation(cPassword:String, password:String):Boolean {
        var valid:Boolean = false
        if (cPassword == password){
            valid = true
        }
        return valid
    }

    fun usernameValidation(username:String):Boolean {
        val valid:Boolean = true
        // TODO: Search if username value is in database
        return valid
    }

    fun emailValidation(email:String):Boolean {
        var valid:Boolean = false
        // Sees if the email exist
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valid = true
        }
        // TODO: Searches if the email address is already being used by a user in the database
        return valid
    }

    fun passwordValidation(password:String):Boolean {
        var valid = false
        // expression that will see if the password contains UPPERCASE and lowercase letter, one number and one special character
        val passwordRegex = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!\-_?&])(?=\\S+$).{8,}""".toRegex()
        if(passwordRegex.matches(password)){
            valid = true
        }
        return valid
    }
}