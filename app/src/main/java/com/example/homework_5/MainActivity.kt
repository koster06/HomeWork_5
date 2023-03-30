package com.example.homework_5

import adapters.ProductsAdapter
import adapters.UsersAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import entities.AddressEntity
import entities.UserAddressEntity
import entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapterProducts : ProductsAdapter
    lateinit var adapterUsers : UsersAdapter
    private lateinit var userRepository: UserRepository
    private val addresses = listOf(
        AddressEntity(0, "ул. Ленина, 10", "Москва", "Россия", "A100Z8"),
        AddressEntity(0, "ул. Пушкина, 5", "Санкт-Петербург", "Россия", "Cr5677o"),
        AddressEntity(0, "ул. Кирова, 20", "Новосибирск", "Россия", "123X8IO"),
        AddressEntity(0, "ул. Ленина, 10", "Москва", "Россия", "A100Z8"),
        AddressEntity(0, "ул. Пушкина, 5", "Санкт-Петербург", "Россия", "Cr5677o"),
        AddressEntity(0, "ул. Кирова, 20", "Новосибирск", "Россия", "123X8IO")
    )
//    private val userAddresses = listOf(
//        UserAddressEntity( 7, 1),
//        UserAddressEntity( 8,2),
//        UserAddressEntity(9, 3) ,
//        UserAddressEntity(10,  4),
//        UserAddressEntity(11, 5),
//        UserAddressEntity(12, 6)
//    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepository(application)
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.apply {
                setAllAddresses(addresses)
//                setAllUserAddresses(userAddresses)
            }
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

/* Retrofit section */

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(REQRES).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        adapterUsers = UsersAdapter()
        binding.apply {
            recyclerConteiner.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerConteiner.adapter = adapterUsers
        }

        CoroutineScope(Dispatchers.IO).launch {
            val userService = retrofit.create(UserService::class.java)
            val page = 2
            val response = userService.getUsers(page)

            val users = response.data
            if (users.isNotEmpty()) {
                for (user in users) {
                    if (userRepository.getUserByEmail(user.email) == null) {
                        userRepository.addUser(
                            UserEntity(
                                id = 0,
                                first_name = user.first_name,
                                last_name = user.last_name,
                                email = user.email,
                                avatar = user.avatar
                            )
                        )
                    }
                }
            }
            val users2 = userRepository.getAllUsers()
            runOnUiThread {
                users2.observe(this@MainActivity) { userList ->
                    adapterUsers.submitList(userList)
                }
            }
        }

    }
//--------------------------------------------------------------------------------------------------

    companion object {
        const val REQRES = "https://reqres.in/api/"
        const val DUMMY = "https://dummyjson.com"
    }
}
// DESCRIPTION
//--------------------------------------------------------------------------------------------------

/*
Задание 10.1
На основе 3 задания из ДЗ 8 переписать логику работы приложения используя liveData и паттерн MVVM ++
*/