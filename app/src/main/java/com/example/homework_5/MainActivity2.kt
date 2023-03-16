package com.example.homework_5

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework_5.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private val adapterNextView = UserNextAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)

        with(binding) {
            with(sharedPreferences) {
                tvName2.text = getString(KEY_NAME, null)
                tvSurname.text= getString(KEY_SURNAME, null)
                tvPhone2.text = getString(KEY_PHONE, null)
                tvAge2.text = getString(KEY_AGE, null)
                tvBirthday2.text = getString(KEY_DATE, null)
                imView.setImageResource(getInt(KEY_IMAGE, 1))
            }
        }

    }

    private companion object {
        const val SHARED_PREF = "myPref"
        const val KEY_NAME = "name"
        const val KEY_SURNAME = "surname"
        const val KEY_PHONE = "phone"
        const val KEY_AGE = "age"
        const val KEY_DATE = "birthday"
        const val KEY_IMAGE = "image"
    }

}


