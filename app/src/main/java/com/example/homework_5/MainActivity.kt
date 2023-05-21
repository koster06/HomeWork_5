package com.example.homework_5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework_5.databinding.ActivityMain3Binding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}



/*------------------- Description----------------

 */