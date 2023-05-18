package adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.UserItemBinding
import com.example.lib.UserLib
import com.example.lib.UserResponseLib

class UserAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(userId: Int)
    }

    private var items = listOf<UserLib>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: UserResponseLib) {
        this.items = items.data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = UserItemBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(user: UserLib) = with(binding){
            nameTextView.text = "${user.first_name} ${user.last_name}"
            emailTextView.text = user.email
            Glide.with(itemView.context)
                .load(user.avatar)
                .circleCrop()
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root).apply {
            itemView.setOnClickListener {

            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = items[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            listener.onItemClick(user.id)
        }
    }

    override fun getItemCount() = items.size

    companion object {
        private var instance: UserAdapter? = null

        fun getInstance(listener: OnItemClickListener): UserAdapter {
            if (instance == null) {
                instance = UserAdapter(listener)
            }
            return instance!!
        }
    }

}
