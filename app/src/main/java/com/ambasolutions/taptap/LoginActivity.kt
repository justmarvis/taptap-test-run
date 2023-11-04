package com.ambasolutions.taptap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // create a redirect to chat page
        val loginButton = findViewById<Button>(R.id.loginBtn)
        loginButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}