package com.example.kotlinmessenger.registerlogin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinmessenger.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            Log.d("Login", "Attempt login with email/pw: $email/pw")

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    // else if successful
                    Log.d("Main", "Successfully signed in user with uid: ${it.result?.user?.uid}")
                }
                .addOnFailureListener {
                    Log.d("Main", "Failed to sign in user: ${it.message}")
                    Toast.makeText(this, "Failed to sign in user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        back_to_register_textview.setOnClickListener {
            finish()
        }
    }
}