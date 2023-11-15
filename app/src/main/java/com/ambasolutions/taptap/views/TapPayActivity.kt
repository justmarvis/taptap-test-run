package com.ambasolutions.taptap.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.ambasolutions.taptap.LauncherActivity
import com.ambasolutions.taptap.R

class TapPayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tap_pay)

        supportActionBar?.title = "Quick Pay"

        // create a redirect for back button
        val bckButton = findViewById<ImageView>(R.id.bckBtn)
        bckButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }
        // create a redirect to the payment methods
        val payerButton = findViewById<Button>(R.id.payerBtn)
        payerButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, LauncherActivity::class.java)
            startActivity(intent)
        }
    }

}