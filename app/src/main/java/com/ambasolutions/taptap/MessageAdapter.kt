package com.ambasolutions.taptap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1) {
            // statement: inflate receive
            val view: View = LayoutInflater.from(context).inflate(R.layout.message_box, parent, false)
            return ReceiverViewHolder(view)
        } else {
            // statement: inflate send
            val view: View = LayoutInflater.from(context).inflate(R.layout.message_box_receiver, parent, false)
            return SenderViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == SenderViewHolder::class.java) {

            // statement: send view holder
            val viewHolder = holder as SenderViewHolder
            holder.senderMessage.text = currentMessage.message

        } else {

            // statement: receiver view holder
            val viewHolder = holder as ReceiverViewHolder
            holder.receiverMessage.text = currentMessage.message

        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SENT
        } else {
            return ITEM_RECEIVE
        }

    }


    class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderMessage = itemView.findViewById<TextView>(R.id.sSend)
    }

    class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiverMessage = itemView.findViewById<TextView>(R.id.rSend)
    }

}