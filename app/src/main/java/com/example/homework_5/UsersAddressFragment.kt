package com.example.homework_5

import Constants.Constants.TEST
import adapters.AddressAdapter
import adapters.UsersAddressAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import entities.AddressEntity
import entities.UserEntity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class UsersAddressFragment: Fragment() {

    private lateinit var usersAddressRecyclerView: RecyclerView
    private lateinit var adapter: UsersAddressAdapter
    lateinit var addressAdapter: AddressAdapter
    private lateinit var userRepository: UserRepository
    private val result = mutableMapOf<UserEntity, MutableList<AddressEntity>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_users_address, container, false)

        usersAddressRecyclerView = view.findViewById(R.id.users_address_recycler_view)
        usersAddressRecyclerView.layoutManager = LinearLayoutManager(context)
        usersAddressRecyclerView.setHasFixedSize(true)

        userRepository = UserRepository(requireActivity().application)
        with(userRepository) {
            //val result = mutableMapOf<UserEntity, MutableList<String>>()
            CoroutineScope(Dispatchers.IO).launch {
                val userAddresses = getUsersAndAddresses()
                addressAdapter = AddressAdapter(object : AddressAdapter.OnItemClickListener {
                    override fun onItemClick(address: AddressEntity) {
                    }
                })
                adapter = UsersAddressAdapter(userAddresses)
                usersAddressRecyclerView.adapter = adapter
            }


//            userAddresses.observe(viewLifecycleOwner) { userAddressList ->
//                lifecycleScope.launch {
//                    userAddressList?.forEach { userAddress ->
//                        val user = getUserById(userAddress.userId)
//                        val address = getAddressById(userAddress.addressId)
//                        Log.d(TEST, "addressId: ${userAddress.addressId}")
//                        Log.d(TEST, "User: ${user?.first_name}, Address: $address")
//                        if (user != null && address != null) {
//                            if (!result.containsKey(user)) {
//                                result[user] = mutableListOf(address)
//                                Log.d(TEST, "Result: $result")
//
//                            } else {
//                                result[user]?.add(address)
//                                Log.d(TEST, "Result 2: $result")
//                            }
//                        }
//                    }
//

//
//                }
//            }
        }

        return view
    }

    // Заглушечный адаптер
    private class PlaceholderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun getItemCount() = 1

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
    }


}