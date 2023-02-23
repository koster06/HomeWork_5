package com.example.homework_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.homework_5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
        shareModel.shareForFragment1.observe(this, {
            binding.textView.text = it
        })

//        binding.bSender.setOnClickListener {
//            shareModel.shareForFragment1.value = binding.etSenderText.text.toString()
//        }

    }

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