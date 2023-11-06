package com.ambasolutions.taptap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // create a redirect to login page
        val quickPayActButton = findViewById<ImageView>(R.id.quickPayBtn)
        quickPayActButton.setOnClickListener {
            // Create an Intent to open the new activity
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }
    }
}