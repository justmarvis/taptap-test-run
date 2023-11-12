package com.ambasolutions.taptap

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ContactActivity : AppCompatActivity() {

    private lateinit var  userRecyclerView: RecyclerView
    private lateinit var  userList: ArrayList<User>
    private lateinit var  adapter: UserAdapter
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

//    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        supportActionBar?.title ="Contacts"

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

//        userRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mDbRef.child("user").addValueEventListener(object: ValueEventListener {

            @SuppressLint("ResourceType")
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear() // this allows the chat view to display all user in database
                for (postSnapshot in snapshot.children) {

                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                    }


//                    userList.add(currentUser!!)

                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        // create a redirect to main page
        val bckButton = findViewById<ImageView>(R.id.pay)
        bckButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        if (item?.itemId == R.id.logout) {
//                // statement: log user out
//                mAuth.signOut()
//                val intent = Intent(this@ContactActivity, MainActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                finish()
//                startActivity(intent)
//                return true
//        }
//        return true
//    }


}