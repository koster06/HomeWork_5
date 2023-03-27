package com.example.homework_5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import com.example.homework_5.databinding.ActivityMain2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit.UserRequest
import retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    lateinit var userRequest: UserRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in").client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFields()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etWork.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFields()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.apply {
            button.setOnClickListener {
                userRequest = UserRequest(
                    etName.text.toString(),
                    etWork.text.toString()
                )
                val userService = retrofit.create(UserService::class.java)
                CoroutineScope(Dispatchers.IO).launch {
                    userService.createUser(userRequest)
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etWork.windowToken, 0)
                clearFields()
            }
        }
    }

    private fun checkFields() {
        val etNameText = binding.etName.text.toString().trim()
        val etWorkText = binding.etWork.text.toString().trim()
        binding.button.isEnabled = etNameText.isNotEmpty() && etWorkText.isNotEmpty()
    }

    private fun clearFields() {
        binding.apply {
            etName.text.clear()
            etWork.text.clear()
        }
    }

}