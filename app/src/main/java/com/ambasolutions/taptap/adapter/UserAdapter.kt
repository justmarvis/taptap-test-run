package com.ambasolutions.taptap.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ambasolutions.taptap.views.ChattingActivity
import com.ambasolutions.taptap.R
import com.ambasolutions.taptap.modal.User
import com.squareup.picasso.Picasso

class UserAdapter(val context: Context, private val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        // Load user image into ImageView using Picasso
        Picasso.get()
            .load(currentUser.imageUrl) // Make sure you have a field for the image URL in your User class
            .placeholder(R.drawable.ic_launcher_foreground) // You can set a placeholder image
            .error(R.drawable.baseline_camera_alt_24) // You can set an error image
            .into(holder.userImageView)

        holder.username.text = currentUser.username

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChattingActivity::class.java)

            intent.putExtra("name", currentUser.username)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)
        }

    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username:TextView = itemView.findViewById(R.id.userName)
        val userImageView:ImageView = itemView.findViewById(R.id.userImage)
    }

}