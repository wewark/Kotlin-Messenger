package com.example.kotlinmessenger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text;
            val password = password_edittext_login.text;

            Log.d("Login", "Attempt login with email/pw: $email/pw")
        }

        back_to_register_textview.setOnClickListener {
            finish()
        }
    }
}