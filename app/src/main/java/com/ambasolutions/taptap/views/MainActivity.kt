package com.ambasolutions.taptap.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ambasolutions.taptap.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        // create a redirect to login page
        val loginActButton = findViewById<Button>(R.id.lvLoginBtn)
        loginActButton.setOnClickListener {
            // Create an Intent to open the new activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // create a redirect to Registration page
        val regActButton = findViewById<Button>(R.id.lvRegisterBtn)
        regActButton.setOnClickListener {
            // Create an Intent to open the new activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}
