package adapters

import Constants.Constants.TEST
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.UsersAddressItemBinding
import entities.UserEntity
import entities.UserWithAddressEntity

class UsersAddressAdapter(
    private val userWithAddressEntity: List<UserWithAddressEntity>
    ) : ListAdapter<UserEntity, UsersAddressAdapter.UsersAddressHolder>(Comparator()){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAddressHolder {
        val binding = UsersAddressItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersAddressHolder(binding.root).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                }
            }
        }
    }

    override fun onBindViewHolder(holder:UsersAddressHolder, position: Int) {
        holder.bind(userWithAddressEntity[position])
    }

    override fun getItemCount(): Int {
        return userWithAddressEntity.size
    }

    class UsersAddressHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = UsersAddressItemBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(user: UserWithAddressEntity) = with(binding) {
            Log.d(TEST, "size of addressList: ")
            tvIdUser.text = user.user.id.toString()
            tvUserName.text = user.user.first_name +" "+ user.user.last_name
            Glide.with(itemView.context)
                .load(user.user.avatar)
                .into(ivAvatar)
        }
    }

    class Comparator: DiffUtil.ItemCallback<UserEntity>()  {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }

    }

}