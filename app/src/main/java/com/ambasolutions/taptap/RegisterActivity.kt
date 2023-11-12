package com.ambasolutions.taptap

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class RegisterActivity : AppCompatActivity() {

    private lateinit var  edtEmail: EditText
    private lateinit var  edtFirstName: EditText
    private lateinit var  edtLastName: EditText
    private lateinit var  edtDoB: EditText
    private lateinit var  edtCountry: EditText
    private lateinit var  edtUserName: EditText
    private lateinit var  edtPassword: EditText
    private lateinit var  btnAgree: Button
    private lateinit var  btnReg: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.Email)
        edtFirstName = findViewById(R.id.First)
        edtLastName = findViewById(R.id.Last)
        edtDoB = findViewById(R.id.DoB)
        edtCountry = findViewById(R.id.Country)
        edtUserName = findViewById(R.id.Username)
        edtPassword = findViewById(R.id.Password)
        btnAgree = findViewById(R.id.agreeBtn)
        btnReg = findViewById(R.id.regBtn)

        btnReg.setOnClickListener {
            val email = edtEmail.text.toString()
            val firstname = edtFirstName.text.toString()
            val lastname = edtLastName.text.toString()
            val dob = edtDoB.text.toString()
            val country = edtCountry.text.toString()
            val username = edtUserName.text.toString()
            val password = edtPassword.text.toString()

            register(email, firstname, lastname, dob, country, username, password)
        }

        // create a redirect to main page
        val bckButton = findViewById<ImageView>(R.id.bckBtn)
        bckButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        val selectPhoto = findViewById<ImageView>(R.id.selectPhoto)
        selectPhoto.setOnClickListener {
            Log.d("RegisterActivity", "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    private var selectedPhotoUri: Uri? = null

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            //proceed and check what the selected images was
            Log.d("RegisterActivity", "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            val selectPhoto = findViewById<ImageView>(R.id.selectPhoto)

            selectPhoto.setImageBitmap(bitmap)

            val bitmapDrawable = BitmapDrawable(bitmap)
            selectPhoto.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun register(email: String, firstname: String, lastname: String, dob: String, country: String, username: String, password: String){
        // user creation statement
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // statement: redirect to landing page
                    addUserToDatabase(email,firstname, lastname, dob, country, username, password, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@RegisterActivity, ChatActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                    uploadImageToFirebaseStorage()
                    finish()
                    startActivity(intent)

                } else {
                    // statement: If registration fails, display a message to the user.
                    val exception = task.exception
                    if (exception != null) {
                        Log.e("RegistrationError", exception.message ?: "Unknown error")
                    }
                    Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Register", "Successfully uploaded image ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "File Location: $it")
                }
            }
    }

    private fun addUserToDatabase(email: String, firstname: String, lastname: String, dob: String, country: String, username: String, password: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(email,firstname, lastname, dob,country, username, password, uid))
    }
}