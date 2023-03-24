package com.example.homework_5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework_5.databinding.ActivityMain2Binding
import user.UserNext
import user.UserNextAdapter


class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private val adapterNextView = UserNextAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        val userList = extras?.getParcelableArrayList<UserNext>("user")
        Log.d("test", "Activity2: ${userList?.size}")
        binding.recyclerNextView.layoutManager = GridLayoutManager(this@MainActivity2, 2)
        binding.recyclerNextView.adapter = adapterNextView
        userList?.forEach {
            adapterNextView.addNextUser(it)
        }
    }
}


