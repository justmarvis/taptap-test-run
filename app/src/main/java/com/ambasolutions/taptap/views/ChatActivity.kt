package com.ambasolutions.taptap.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ambasolutions.taptap.R
import com.ambasolutions.taptap.adapter.ChatAdapter
import com.ambasolutions.taptap.modal.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chtRecyclerView: RecyclerView
    private lateinit var chtList: ArrayList<User>
    private lateinit var adapter: ChatAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        chtList = ArrayList()
        adapter = ChatAdapter(this, chtList)

        chtRecyclerView = findViewById(R.id.chtRecyclerView)
        chtRecyclerView.layoutManager = LinearLayoutManager(this)
        chtRecyclerView.adapter = adapter

        mDbRef.child("users").addValueEventListener(object : ValueEventListener {

            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                chtList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid != currentUser?.uid) {
                        chtList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        // create a redirect to the main page
        val bckButton = findViewById<ImageView>(R.id.pay)
        bckButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.contacts -> {
                val intent = Intent(this@ChatActivity, ContactActivity::class.java)
                startActivity(intent)
            }
            R.id.logout -> {
                // statement: log the user out
                mAuth.signOut()
                val intent = Intent(this@ChatActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                finish()
                startActivity(intent)
                return true
            }
        }
        return true
    }
}
