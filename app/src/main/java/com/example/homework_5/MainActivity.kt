package com.example.homework_5

import Constants.Constants.REQRES
import adapters.UsersAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
import entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modules.ApplicationModule
import modules.DaggerMyComponent
import modules.UserRepositoryModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import viewmodels.MyViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapterUsers: UsersAdapter

    @Inject
    lateinit var myViewModel: MyViewModel

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerMyComponent.builder()
            .applicationModule(ApplicationModule(application))
            .userRepositoryModule(UserRepositoryModule(application))
            .build()
            .inject(this)

        userRepository = UserRepository(application)

/* Retrofit section */

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(REQRES).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

/* Adapter section */

        adapterUsers = UsersAdapter()
        binding.apply {
            recyclerConteiner.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerConteiner.adapter = adapterUsers
            button.setOnClickListener {
                val intent = Intent(this@MainActivity, ActivityAddressesList::class.java)
                startActivity(intent)
            }
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
}

// DESCRIPTION
//--------------------------------------------------------------------------------------------------

/*
На экране реализовать UI для отображения списка адресов.
 Реализовать логику выбора адреса пользователем (По нажатию на элемент выбор можно записывать в
 SharedPreferences или в отдельную таблицу или в файл по вашему выбору).
Добавить кнопку сохранить, при нажатии на которую в таблицу "адрес пользователя" будет записываться
данные пользователя и адрес, который он выбрал.
 После сохранения данных перенаправьте пользователя на 3-й экран,
 где отобразить список пользователей и их адреса.
 Добавьте кнопку, при нажатии на которую, будут оставаться только пользователи, у которых есть адрес
 Добавьте поле ввода, при вводе должен осуществляться поиск по списку пользователей.
 Логику поиска придумайте сами (поиск после нажатия на кнопку или при вводе в поле или другое)
*/