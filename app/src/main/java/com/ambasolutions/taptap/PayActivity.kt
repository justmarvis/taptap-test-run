package com.ambasolutions.taptap

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class PayActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        // create a redirect to main page
        val payButton = findViewById<Button>(R.id.payBtn)
        payButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, TapPayActivity::class.java)
            startActivity(intent)
        }
    }
}