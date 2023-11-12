package com.ambasolutions.taptap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var  edtEmail: EditText
    private lateinit var  edtPassword: EditText
    private lateinit var  btnLog: Button

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.email)
        edtPassword = findViewById(R.id.password)
        btnLog = findViewById(R.id.loginBtn)

        btnLog.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "email and password are required", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // statement: redirect to landing page
                            val intent = Intent(this@LoginActivity, ChatActivity::class.java)
                            finish()
                            startActivity(intent)

                        } else {
                            // statement: If login fails, display a message to the user.
                            val exception = task.exception
                            if (exception != null) {
                                Log.e("LoginError", exception.message ?: "Unknown error")
                            }
                            Toast.makeText(this@LoginActivity, "username or password is incorrect", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }

//            login(email, password)
        }

        // create a redirect to main page
        val bckButton = findViewById<ImageView>(R.id.loginBckBtn)
        bckButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}