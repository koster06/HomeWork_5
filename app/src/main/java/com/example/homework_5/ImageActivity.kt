package com.example.homework_5

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val data: Uri? = intent.data
        Log.d("test", data.toString())
        if (data != null) {
            val imageUrl = data.toString()
            loadImage(imageUrl)
        }
    }
    private fun loadImage(imageUrl: String) {
        val imageView:ImageView = findViewById(R.id.image_View)
        Log.d("test", "uuu")
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }

}