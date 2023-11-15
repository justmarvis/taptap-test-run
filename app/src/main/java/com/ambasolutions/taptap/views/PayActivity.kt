package com.ambasolutions.taptap.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.ambasolutions.taptap.R

class PayActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        supportActionBar?.title = "Quick Pay"

        // create a redirect to main page
        val payButton = findViewById<Button>(R.id.payBtn)
        payButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        // create a redirect to main page
        val chatButton = findViewById<ImageView>(R.id.chatBtn)
        chatButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}