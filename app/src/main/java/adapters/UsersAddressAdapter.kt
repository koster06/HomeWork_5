package adapters

import Constants.Constants.TEST
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.UsersAddressItemBinding
import entities.UserEntity
import entities.UserWithAddressEntity

class UsersAddressAdapter(
    private val userWithAddressEntity: List<UserWithAddressEntity>
) : RecyclerView.Adapter<UsersAddressAdapter.UsersAddressHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAddressHolder {
        val binding = UsersAddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersAddressHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersAddressHolder, position: Int) {
        holder.bind(userWithAddressEntity[position])
    }

    override fun getItemCount(): Int {
        return userWithAddressEntity.size
    }

    inner class UsersAddressHolder(private val binding: UsersAddressItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(userWithAddressEntity: UserWithAddressEntity) = with(binding) {
            tvUserName.text = userWithAddressEntity.user.first_name + " " + userWithAddressEntity.user.last_name

            Glide.with(itemView.context)
                .load(userWithAddressEntity.user.avatar)
                .into(ivAvatar)

            // Dynamically create a table row for each address in the user's list
            userWithAddressEntity.addresses.forEach { address ->
                val tableRow = TableRow(itemView.context)

                val addressColumn = TextView(itemView.context)
                addressColumn.text = address.toString()
                addressColumn.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                )

                tableRow.addView(addressColumn)
                tableLayout.addView(tableRow)
            }
        }
    }
}


