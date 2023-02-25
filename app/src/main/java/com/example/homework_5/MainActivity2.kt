package com.example.homework_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework_5.databinding.ActivityMain2Binding
import com.example.homework_5.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity(), Communicator {

    lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.place_holder21, Fragment21())
            .commit()
    }


        override fun passData(editTextInput: String) {
            val bundle = Bundle()
            bundle.putString("message", editTextInput)

            val transaction = this.supportFragmentManager.beginTransaction()
            val fragment2 = Fragment22()

            fragment2.arguments = bundle
            transaction.addToBackStack(null)
            transaction.replace(R.id.place_holder21,fragment2)
            transaction.commit()
        }

}