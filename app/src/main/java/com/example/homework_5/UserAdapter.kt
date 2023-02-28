package com.example.homework_5

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_5.databinding.CardItemBinding

class UserAdapter(private val adapterListener: AdapterListener) : RecyclerView.Adapter<UserAdapter.UserHolder>(),
    View.OnClickListener {

    var userList = mutableListOf<User>()

    class UserHolder(item: View) : RecyclerView.ViewHolder(item) {

        val binding = CardItemBinding.bind(item)
        fun bind(user: User) = with(binding) {//метод будет связывать все элементы холдера с полями bird:Bird
            imV.setImageResource(user.id)
            tvName.text = user.name
            tvSecond.text = user.secName
            tvPhone.text = user.phone.toString()
            tvAge.text = user.age.toString()
            tvBirthday.text = user.birthday
            bRemove.tag = user//добавил тэг, чтоб адаптер знал номер холдера для вызова нажатия (иначе: -1)
        }                       // его можно добавлять для всего холдера, текста и прочего, чтоб по нажатию
                               // OnClickListener посылал адаптеру номер вьюхи (иначе: -1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardItemBinding.inflate(inflater,parent,false)
        binding.bRemove.setOnClickListener(this)
        return UserHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onClick(v: View) {
        val user = v.tag as User  // здесь нам этот тэг и нужен!
        Log.d("test", "Нажали на: ${user.id}")
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
        notifyDataSetChanged()//notifyItemInserted(), notifyItemRemoved(), notifyItemChanged()
        // - методы, отслеживающие добавление, удаление или изменение позиции одного элемента
        // находятся в RecyclerView.Adapter
    }

}