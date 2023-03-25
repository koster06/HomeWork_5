package adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_5.UserNext
import com.example.homework_5.databinding.CardNextViewBinding

class UserNextAdapter: RecyclerView.Adapter<UserNextAdapter.UserNextHolder>() {

    var userList = mutableListOf<UserNext>()

    class UserNextHolder(item: View):RecyclerView.ViewHolder(item) {
        private val  binding = CardNextViewBinding.bind(item)
        fun bind(userNext: UserNext) {
            binding.imNextView.setImageResource(userNext.id)
            binding.tvNextView.text = userNext.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserNextHolder {
        Log.d("test", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardNextViewBinding.inflate(inflater,parent,false)
        return UserNextHolder(binding.root)
    }

    override fun getItemCount(): Int {
        Log.d("test", "getItemCount ${userList.size}")
        getSize()
        return userList.size
    }

    override fun onBindViewHolder(holder: UserNextHolder, position: Int) {
        Log.d("test", "onBindViewHolder")
        holder.bind(userList[position])
    }
    fun getSize():Int {
     Log.d("test","${userList.size}")
        return userList.size
    }

    fun addNextUser(userNext: UserNext) {
        userList.add(userNext)
        Log.d("test", "addUser: ${userList.size}")
        notifyDataSetChanged()
        userList.forEach{
            Log.d("test", "user# ${it.id}")
        }
        getSize()
    }

}