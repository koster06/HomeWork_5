package com.example.homework_5

import android.R
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.example.homework_5.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding
    private val adapter: UserAdapter = UserAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)



    }


    fun setImageViewColor(bitmap: Bitmap) {
        // Generate the palette and get the vibrant swatch
        val vibrantSwatch = createPaletteSync(bitmap).darkMutedSwatch

        // Set the toolbar background and text colors.
        // Fall back to default colors if the vibrant swatch is not available.
//        with(binding.imageView) {
//            setBackgroundColor(vibrantSwatch?.rgb ?:
//                ContextCompat.getColor(context, R.color.white))
            //}
    }
    private fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()


}


