package com.ambasolutions.taptap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val username = intent.getStringExtra("username")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        supportActionBar?.title = username



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

                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear() // this allows the chat recycler view to display all data in database
                    for(postSnapshot in snapshot.children) {

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()

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

        // create a redirect to landing page
        val bckButton = findViewById<ImageView>(R.id.bckBtn)
        bckButton.setOnClickListener {
            // Create an Intent to open Chat activity
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}