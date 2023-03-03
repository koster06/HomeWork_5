package com.example.homework_5

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_5.databinding.CardItemBinding
import com.example.homework_5.databinding.CardNextViewBinding

class UserNextViewAdapter() : RecyclerView.Adapter<UserNextViewAdapter.UserNextViewHolder>() {
    private val adapter = UserAdapter()
    var userNextViewList = adapter.userList

    class UserNextViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CardNextViewBinding.bind(item)
        fun bind(user: User) = with(binding) {
            imNextView.setImageResource(user.id)
            tvNextView.text = user.name
        }
    }


    fun addUser(user: User) {
        userNextViewList.add(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserNextViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardNextViewBinding.inflate(inflater,parent,false)
        return UserNextViewAdapter.UserNextViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return userNextViewList.size
    }

    override fun onBindViewHolder(holder: UserNextViewHolder, position: Int) {
        holder.bind(userNextViewList[position])
    }

}