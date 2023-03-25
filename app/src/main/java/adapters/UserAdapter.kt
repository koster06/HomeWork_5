package adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_5.R
import retrofit.User
import com.example.homework_5.databinding.CardItemBinding

class UserAdapter(private val adapterListener: AdapterListener) : RecyclerView.Adapter<UserAdapter.UserHolder>(),
    View.OnClickListener {

    var userList = mutableListOf<User>()
    class UserHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CardItemBinding.bind(item)
        fun bind(user: User) = with(binding) {
            //imV.setImageResource(user.id)
            tvName.text = user.firstName
            tvSecond.text = user.lastName
            tvPhone.text = user.email
            tvAge.text = null
            tvBirthday.text = null
            bRemove.tag = user
            Log.i("test", "UserHolder: ${user.email}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardItemBinding.inflate(inflater,parent,false)
        binding.bRemove.setOnClickListener(this)
        return UserHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userList[position])
        Log.i("test", "onBindViewHolder: ${userList[position]}")
    }

    override fun getItemCount(): Int {
        Log.i("test", "getItemCount: ${userList.size} \n ----------")
        return userList.size
    }

    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
            R.id.bRemove -> {
                adapterListener.removeUser(user)
            }
            else -> {
                //TODO something
            }
        }
    }

    fun addUser(user: User) {
        userList.add(user)
        Log.i("test", "addUser size: ${userList.size}")
        notifyDataSetChanged()
    }
}