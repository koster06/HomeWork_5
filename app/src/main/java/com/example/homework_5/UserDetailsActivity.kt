package com.example.homework_5

import Constants.Constants.TEST
import Constants.Constants.USERID
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.ActivityUserDetailsBinding
import entities.AddressEntity
import entities.UserAddressEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import viewmodels.MyViewModel

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var userRepository: UserRepository
    private val myViewModel: MyViewModel by viewModels()
    private val fragment = AddressListFragment()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra(USERID, -1)

        userRepository = UserRepository(application)

        binding.button4.setOnClickListener {
            showAddresses()
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
        myViewModel.messageForActivity.observe(this){
            with(binding) {
                Log.i(TEST, it.city)
                etPersonStreet.setText(it.street)
                etPersonCity.setText(it.city)
                etPersonState.setText(it.state)
                etPersonZip.setText(it.zip)
            }
        }
        myViewModel.messageForCloseFragment.observe(this){
            if (it){
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.fade_in, R.anim.fade_out)
                    remove(fragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }

    }

    private fun showAddresses() {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.fade_in, R.anim.fade_out)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

}