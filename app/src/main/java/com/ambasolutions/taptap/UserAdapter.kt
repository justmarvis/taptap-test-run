package com.ambasolutions.taptap

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context, val userList: ArrayList<User>):
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

        holder.username.text = currentUser.username

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChattingActivity::class.java)

            intent.putExtra("name", currentUser.username)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)
        }

    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username:TextView = view.findViewById(R.id.userName)
        val imageUser:ImageView = view.findViewById(R.id.userImage)
    }

}