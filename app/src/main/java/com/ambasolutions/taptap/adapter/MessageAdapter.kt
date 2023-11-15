package com.ambasolutions.taptap.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambasolutions.taptap.modal.Message
import com.ambasolutions.taptap.R
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1) {
            // statement: inflate receive
            val view: View = LayoutInflater.from(context).inflate(R.layout.message_box_receiver, parent, false)
            return ReceiverViewHolder(view)
        } else {
            // statement: inflate send
            val view: View = LayoutInflater.from(context).inflate(R.layout.message_box, parent, false)
            return SenderViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder is SenderViewHolder) {
            // statement: send view holder
            val viewHolder = holder
            viewHolder.senderMessage.text = currentMessage.message

            // Load sender image into ImageView using Picasso
            val senderImageUrl = currentMessage.imageUrl
            if (!senderImageUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(senderImageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground) // Placeholder for loading image
                    .error(R.drawable.baseline_camera_alt_24) // Error image if loading fails
                    .into(viewHolder.senderImageView, )
                Log.d("MessageAdapter", "Sender Image URL: $senderImageUrl")
            } else {
                // Handle the case where senderImageUrl is null or empty
                viewHolder.senderImageView.setImageResource(R.drawable.ic_launcher_foreground)
            }
        } else if (holder is ReceiverViewHolder) {
            // statement: receiver view holder
            val viewHolder = holder
            viewHolder.receiverMessage.text = currentMessage.message

            // Load receiver image into ImageView using Picasso
            val receiverImageUrl = currentMessage.imageUrl
            if (!receiverImageUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(receiverImageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground) // Placeholder for loading image
                    .error(R.drawable.baseline_camera_alt_24) // Error image if loading fails
                    .into(viewHolder.receiverImageView)
                Log.d("MessageAdapter", "Receiver Image URL: $receiverImageUrl")
            } else {
                // Handle the case where receiverImageUrl is null or empty
                viewHolder.receiverImageView.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
    }

//    private fun setTime(viewHolder: ViewHolder) {
//        val dateFormat = SimpleDateFormat
//            .getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
//        viewHolder.textView_message_time.text = dateFormat.format(message.time)
//    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SENT
        } else {
            return ITEM_RECEIVE
        }

    }


    class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val senderMessage = itemView.findViewById<TextView>(R.id.sSend)
        val senderMessage = itemView.findViewById<TextView>(R.id.msg_txt)
        val senderImageView = itemView.findViewById<ImageView>(R.id.userImage)
    }

    class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val receiverMessage = itemView.findViewById<TextView>(R.id.rSend)
        val receiverMessage = itemView.findViewById<TextView>(R.id.msg_txt)
        val receiverImageView = itemView.findViewById<ImageView>(R.id.userImage)
    }

}