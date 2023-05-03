package com.example.homework_5

import Constants.Constants.TEST
import adapters.AddressAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import entities.AddressEntity
import viewmodels.MyViewModel

class AddressListFragment : Fragment() {

    private lateinit var addressRecyclerView: RecyclerView
    private lateinit var adapter: AddressAdapter
    private lateinit var userRepository: UserRepository
    private val addresses = listOf(
        AddressEntity(0, "ул. Ленина, 10", "Москва", "Россия", "ZZZZZZ"),
        AddressEntity(0, "ул. Пушкина, 5", "Санкт-Петербург", "Россия", "Cr5677o"),
        AddressEntity(0, "ул. Кирова, 20", "Новосибирск", "Россия", "123X8IO"),
        AddressEntity(0, "пр. Мира, 1", "Минск", "Беларусь", "220001"),
        AddressEntity(0, "ул. Лермонтова, 15а", "Екатеринбург", "Китай", "Cr5677o"),
        AddressEntity(0, "str. Appolon, 2", "L.A", "USA", "903XPZO",),
        AddressEntity(0, "str. Love, 22", "Brooklin", "USA", "1YU9PYO",),
        AddressEntity(0, "ул. Дураков, 9", "Слуцк", "Беларусь", "224600",),
        AddressEntity(0, "ул. Лукашенки-КГБ, 666", "Лида", "Беларусь", "226777",)
    )
    private val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_address_list, container, false)
        Log.d(TEST, TEST)
        userRepository = UserRepository(requireActivity().application)
        addressRecyclerView = view.findViewById(R.id.address_recycler_view)
        addressRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AddressAdapter(object : AddressAdapter.OnItemClickListener {
            override fun onItemClick(address: AddressEntity) {
                with(myViewModel) {
                    messageForActivity.value = address
                    messageForCloseFragment.value = true
                }
            }
        })
        addressRecyclerView.adapter = adapter
        adapter.submitList(addresses)

        return view
    }

}
