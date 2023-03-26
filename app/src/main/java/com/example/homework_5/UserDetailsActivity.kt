package com.example.homework_5

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.ActivityUserDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit.Support
import retrofit.User
import retrofit.UserResponse
import retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var userApi: UserService


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()  // для вывода информации о статусе подключения в логи
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/").client(client) //для вывода информации о статусе подключения в логи
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userApi = retrofit.create(UserService::class.java)


        // получаем id пользователя из Intent
        val userId = intent.getIntExtra("userId", -1)

        // загружаем информацию о пользователе по id
        CoroutineScope(Dispatchers.IO).launch {
            val user = loadUser(userId)
            val support = loadSupport(userId)
            runOnUiThread{
                with(binding) {
                    userName.text = "${user?.first_name} ${user?.last_name}"
                    userEmail.text = user?.email
                    userInfo.text = "${support?.text} \n ${support?.url}"
                    Glide.with(this@UserDetailsActivity)
                        .load(user?.avatar)
                        .into(userAvatar)
                }
            }
        }
    }

    private suspend fun loadUser(userId: Int): User? {
        return try {
            //Log.i("test", "$userId")
            val userResponse = userApi.getUser(userId)
            userResponse.data
        } catch (e: Exception) {
            Log.e("test", "Error loading user: ${e.message}")
            null
        }
    }

    private suspend fun loadSupport (userId: Int): Support? {
        return try {
            //Log.i("test", "$userId")
            val userResponse = userApi.getUser(userId)
            userResponse.support
        } catch (e: Exception) {
            Log.e("test", "Error loading user: ${e.message}")
            null
        }
    }

}
