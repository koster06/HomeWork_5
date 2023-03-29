package adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_5.UserDetailsActivity
import com.example.homework_5.databinding.ItemUserBinding
import entities.UserEntity


class UsersAdapter : ListAdapter<UserEntity, UsersAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemUserBinding.bind(view)
        private val avatar: ImageView = binding.avatar

        @SuppressLint("SetTextI18n")
        fun bind(user: UserEntity) = with(binding){
            name.text = "${user.first_name} ${user.last_name}"
            email.text = user.email
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(avatar)
        }
    }

    class Comparator: DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding.root).apply {
            itemView.setOnClickListener {
                val position = adapterPosition //bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val user = getItem(position)
                    val context = itemView.context
                    val intent = Intent(context, UserDetailsActivity::class.java).apply {
                        // передаем id пользователя на новый экран
                        putExtra("userId", user.id)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

}