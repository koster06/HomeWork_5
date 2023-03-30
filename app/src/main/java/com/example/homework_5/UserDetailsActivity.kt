package com.example.homework_5

import Constants.Constants.USERID
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.ActivityUserDetailsBinding
import entities.AddressEntity
import entities.UserAddressEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra(USERID, -1)
        Log.i("test", "id: $userId")

        userRepository = UserRepository(application)

        with(binding) {
            button4.setOnClickListener {
                val address = addresses.get(getRandomNumber())
                etPersonStreet.setText(address.street)
                etPersonCity.setText(address.city)
                etPersonState.setText(address.state)
                etPersonZip.setText(address.zip)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getUserById(userId)
            runOnUiThread{
                with(binding) {
                    userName.text = "${user?.first_name} ${user?.last_name}"
                    userEmail.text = user?.email
                    Glide.with(this@UserDetailsActivity)
                        .load(user?.avatar)
                        .into(userAvatar)
                }
            }
            with(binding) {
                button3.setOnClickListener {
                    val address = AddressEntity(
                        0,
                        street = etPersonStreet.text.toString(),
                        city = etPersonCity.text.toString(),
                        state = etPersonState.text.toString(),
                        zip = etPersonZip.text.toString()
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        with(userRepository) {
                            val addressId = addAddress(address)
                            addUserAddress(UserAddressEntity(
                                0,
                                userId,
                                addressId
                            ))
                        }
                    }
                }
            }
        }
    }
    private fun getRandomNumber(): Int {
        return (0..8).random()
    }
}
