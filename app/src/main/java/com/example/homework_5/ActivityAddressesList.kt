package com.example.homework_5

import Constants.Constants.ADDRESS_KEY
import Constants.Constants.MY_PREFERENCES
import Constants.Constants.TEST
import adapters.AddressAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_5.databinding.ActivityAddressesListBinding
import com.google.gson.Gson
import entities.AddressEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ActivityAddressesList : AppCompatActivity() {

    private lateinit var binding: ActivityAddressesListBinding
    lateinit var adapterAddress: AddressAdapter
    private lateinit var userRepository : UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterAddress = AddressAdapter(object : AddressAdapter.OnItemClickListener {
            override fun onItemClick(address: AddressEntity) {
                saveToSharedPref(address)
            }
        })
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@ActivityAddressesList)
            recyclerView.adapter = adapterAddress
        }

        userRepository = UserRepository(application)

        CoroutineScope(Dispatchers.IO).launch {
            val addresses = userRepository.getAllAddresses()
            runOnUiThread {
                addresses.observe(this@ActivityAddressesList) { addressList ->
                    Log.i(TEST,"${addressList.size}")
                    adapterAddress.submitList(addressList)
                }
            }
        }

        val context = applicationContext
        val sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
        val addressJson = sharedPreferences.getString(ADDRESS_KEY, null)
        val address: AddressEntity? = Gson().fromJson(addressJson, AddressEntity::class.java)
    }

    private fun saveToSharedPref(address:AddressEntity){
        val sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val addressJson = Gson().toJson(address)
        editor.putString(ADDRESS_KEY, addressJson)
        editor.apply()
    }

}
// DESCRIPTION
//--------------------------------------------------------------------------------------------------

/*
На экране реализовать UI для отображения списка адресов.++
 Реализовать логику выбора адреса пользователем (По нажатию на элемент выбор можно записывать в
 SharedPreferences или в отдельную таблицу или в файл по вашему выбору).++
Добавить кнопку сохранить, при нажатии на которую в таблицу "адрес пользователя" будет записываться
данные пользователя и адрес, который он выбрал.++
 После сохранения данных перенаправьте пользователя на 3-й экран,
 где отобразить список пользователей и их адреса. ++
 Добавьте кнопку, при нажатии на которую, будут оставаться только пользователи, у которых есть адрес --
 Добавьте поле ввода, при вводе должен осуществляться поиск по списку пользователей. ++
 Логику поиска придумайте сами (поиск после нажатия на кнопку или при вводе в поле или другое) ++
*/