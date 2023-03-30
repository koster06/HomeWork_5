package com.example.homework_5

import adapters.UsersAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
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
    lateinit var adapterUsers : UsersAdapter
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepository(application)
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.apply {
//                setAllAddresses(addresses)
//                setAllUserAddresses(userAddresses)
            }
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