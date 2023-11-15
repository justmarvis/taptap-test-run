package com.ambasolutions.taptap.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambasolutions.taptap.modal.User
import com.ambasolutions.taptap.R
import com.ambasolutions.taptap.views.ChattingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ChatAdapter(private val context: Context, private val chatList: ArrayList<User>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private lateinit var mDbRef: DatabaseReference
    private var senderUid: String? = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.chat_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val currentChatUser = chatList[position]
        val receiverUid = currentChatUser.uid

        mDbRef = FirebaseDatabase.getInstance().getReference()
        val senderRoom = senderUid + receiverUid
        var receiverRoom = receiverUid + senderUid

        // Load user image into ImageView using Picasso
        Picasso.get()
            .load(currentChatUser.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.baseline_camera_alt_24)
            .into(holder.chatUserImageView)

        holder.chatUsername.text = currentChatUser.username

        // Fetch recent chat messages from the database and update the User object
        fetchRecentChatMessages(senderRoom, holder.recentChat)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChattingActivity::class.java)

            // Use data from currentChatUser to set extras
            intent.putExtra("name", currentChatUser.username)
            intent.putExtra("uid", currentChatUser.uid)

            context.startActivity(intent)
        }
    }

    private fun fetchRecentChatMessages(roomId: String, textView: TextView) {
        mDbRef.child("chats").child(roomId).child("messages")
            .orderByKey()
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val recentMessages = StringBuilder()

                    for (messageSnapshot in snapshot.children) {
                        val messageText = messageSnapshot.child("message").getValue(String::class.java)
                        recentMessages.append("$messageText\n")
                    }

                    textView.text = if (recentMessages.isNotEmpty()) {
                        recentMessages.toString()
                    } else {
                        ""
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                    Log.e("ChatAdapter", "Failed to fetch recent chat messages: ${error.message}")
                }
            })
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatUsername: TextView = itemView.findViewById(R.id.userName)
        val chatUserImageView: ImageView = itemView.findViewById(R.id.userImage)
        val recentChat: TextView = itemView.findViewById(R.id.recentChat)
    }
}

