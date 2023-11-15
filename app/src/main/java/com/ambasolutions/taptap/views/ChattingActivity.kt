package com.ambasolutions.taptap.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ambasolutions.taptap.modal.Message
import com.ambasolutions.taptap.R
import com.ambasolutions.taptap.adapter.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChattingActivity : AppCompatActivity() {

    private lateinit var  chatRecyclerView: RecyclerView
    private lateinit var  type: EditText
    private lateinit var  sndButton: ImageView
    private lateinit var  messageAdapter: MessageAdapter
    private lateinit var  messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

//        supportActionBar?.title = null

        // Assuming you have a TextView representing the latest message in your layout
        chatRecyclerView = findViewById(R.id.chatRecyclerView)

        // Example: Trigger the zoom effect when a new message is sent or received
        simulateNewMessage()

        // Get the reference to the database
        val database = FirebaseDatabase.getInstance().reference

        // Get the reference to the user's username
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val usernameRef = database.child("users").child(currentUserId ?: "").child("username")
        val odaUsernameRef = database.child("users").child("1").child("username")

        //        val usernameRef = currentUserId?.let { database.child("users").child(it).child("username") }


        val username = intent.getStringExtra("username")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

//        username = findViewById<TextView>(R.id.userName).toString()
//        usernamePlaceholder = username

//        supportActionBar?.title = username

        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid


        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        type = findViewById(R.id.type)
        sndButton = findViewById(R.id.sndButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter( this,messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

//        messageRecyclerView.smoothScrollToPosition(messageList.size - 1)


        //add message to recyclerView
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {

                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear() // this allows the chat recycler view to display all data in database
                    for(postSnapshot in snapshot.children) {

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()

                    chatRecyclerView.scrollToPosition(messageAdapter.itemCount - 1)

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })



        // add message to database
        sndButton.setOnClickListener {
            val message = type.text.toString()
            val messageObject = Message(message, senderUid)

            mDbRef.child( "chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child( "chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            type.setText("")
        }

        // Read the value of the user
        usernameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val username = dataSnapshot.getValue(String::class.java)

                // Display the username on the action
    //                val usernamePlaceholder = findViewById<TextView>(R.id.userName)

    //                usernamePlaceholder.text = username ?: "Unknown User"
                supportActionBar?.title = username ?: ""
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })

        // Read the value of the receiving user
        odaUsernameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val username = dataSnapshot.getValue(String::class.java)

                // Display the receiving username on the action
                    val usernamePlaceholder = findViewById<TextView>(R.id.userName)

                    usernamePlaceholder.text = username ?: "Unknown User"
//                supportActionBar?.title = username ?: ""
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }

    private fun simulateNewMessage() {
        // Simulate receiving a new message
        val newMessage = "Hello there! This is a new message."

        // Set the text of the latest message TextView
//        chatRecyclerView.text = newMessage

        // Apply the zoom effect
        zoomInLatestMessage()
    }

    private fun zoomInLatestMessage() {
       chatRecyclerView.apply {
            scaleX = 0f
            scaleY = 0f
            visibility = View.VISIBLE

            // Use ViewPropertyAnimator for the zoom effect
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(150) // Adjust the duration as needed
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }


}