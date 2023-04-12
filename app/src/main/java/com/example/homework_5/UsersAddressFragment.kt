package com.example.homework_5

import Constants.Constants.TEST
import adapters.AddressAdapter
import adapters.UsersAddressAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import entities.AddressEntity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fabSearch = view.findViewById<FloatingActionButton>(R.id.search_fab)
        fabSearch.setOnClickListener {
            showSearchDialog()
        }
    }

    private fun showSearchDialog() {
        val inputView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_search, null)
        val inputText = inputView.findViewById<EditText>(R.id.edit_text_search)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Search")
            .setView(inputView)
            .setPositiveButton("Search") { _, _ ->
                val searchText = inputText.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val allUsers = userRepository.getUsersAndAddresses()
                    val filteredUsers = allUsers.filter { user ->
                        user.user.first_name.contains(searchText, ignoreCase = true) || user.user.last_name.contains(
                            searchText,
                            ignoreCase = true
                        )
                    }
                    withContext(Dispatchers.Main) {
                        adapter = UsersAddressAdapter(filteredUsers)
                        usersAddressRecyclerView.adapter = adapter
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        alertDialog.show()
    }


}