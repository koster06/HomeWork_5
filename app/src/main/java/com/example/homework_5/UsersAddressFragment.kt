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
            CoroutineScope(Dispatchers.IO).launch {
                val userAddresses = getUsersAndAddresses()
                addressAdapter = AddressAdapter(object : AddressAdapter.OnItemClickListener {
                    override fun onItemClick(address: AddressEntity) {
                    }
                })
                adapter = UsersAddressAdapter(userAddresses)
                usersAddressRecyclerView.adapter = adapter
            }
        }

        return view
    }
}