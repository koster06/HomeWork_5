package com.example.homework_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.homework_5.databinding.ActivityMainBinding
import com.example.homework_5.databinding.Fragment2Binding
import java.util.*

class MainActivity : AppCompatActivity() {

    val myCalendar = Calendar.getInstance()
    lateinit var binding: ActivityMainBinding
    private val shareModel: DataShare by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("test", "Activity: onCreate")

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null) //added for looking what happens when will pressed "back"
            .replace(R.id.place_holder,Fragment1.newInstance())
            .commit()

        shareModel.shareForActivity.observe(this, {
            callFragment2(it)
        })

        shareModel.shareForActivity.observe(this, {
            callFragment1(it)
        })

        binding.bSender.setOnClickListener {
            shareModel.shareForFragment1.value = binding.etSenderText.text.toString()
            binding.etSenderText.text.clear()
        }

        binding.radioButton1.setOnClickListener {
            shareModel.shareForFragment1Boolean.value = true
        }
        binding.radioButton2.setOnClickListener {
            shareModel.shareForFragment1Boolean.value = false
        }

        binding.tvDateSender.setOnClickListener {
            shareModel.shareForFragment1Date.value = myCalendar.time
        }

    }
    fun callFragment2 (b: Boolean) {
        if (b)
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null) //added for looking what happens when will pressed "back"
                .replace(R.id.place_holder,Fragment2.newInstance())
                .commit()
    }
    fun callFragment1 (b: Boolean) {
        if (!b)
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null) //added for looking what happens when will pressed "back"
                .replace(R.id.place_holder,Fragment1.newInstance())
                .commit()
    }
    /*
    Задание 3
Создать второй фрагмент  на Java -- может быть, но не сегодня, не в мою смену!
В первом фрагменте добавить FrameLayout -- он и так там был... Что имеется ввиду?
Второй фрагмент вызвать из первого фрагмента
Передать во второй фрагмент параметры, переданные из активити
     */

    override fun onStart() {
        super.onStart()
        Log.d("test", "Activity: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("test", "Activity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("test", "Activity: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("test", "Activity: onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("test", "Activity: onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test", "Activity: onDestroy")
    }

}