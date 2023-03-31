package com.example.homework_5

import adapters.AddressAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_5.databinding.ActivityAddressesListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityAddressesList : AppCompatActivity() {

    lateinit var binding: ActivityAddressesListBinding
    lateinit var adapterAddress: AddressAdapter
    private lateinit var userRepository : UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterAddress = AddressAdapter()
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@ActivityAddressesList)
            recyclerView.adapter = adapterAddress
        }

        userRepository = UserRepository(application)

        CoroutineScope(Dispatchers.IO).launch {
            val addresses = userRepository.getAllAddresses()
            runOnUiThread {
                addresses.observe(this@ActivityAddressesList) { addressList ->
                    adapterAddress.submitList(addressList)
                }
            }
        }

    }
}