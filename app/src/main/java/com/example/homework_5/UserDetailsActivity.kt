package com.example.homework_5

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.ActivityUserDetailsBinding
import entities.AddressEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var userRepository: UserRepository

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("userId", -1)
        Log.i("test", "id: $userId")

        userRepository = UserRepository(application)

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
                        userRepository.addAddress(address)
                    }
                }
            }
        }
    }
}
