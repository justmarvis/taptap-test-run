package com.ambasolutions.taptap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // create a redirect to chat page
        val loginButton = findViewById<Button>(R.id.regBtn)
        loginButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        // create a redirect to main page
        val bckButton = findViewById<ImageView>(R.id.bckBtn)
        bckButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}