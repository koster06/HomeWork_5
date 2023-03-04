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
        val extras = intent.extras
        val userList = extras?.getParcelableArrayList<UserNext>("user")
        Log.d("test", "Activity2: ${userList?.size}")
        binding.recyclerNextView.layoutManager = GridLayoutManager(this@MainActivity2, 2)
        binding.recyclerNextView.adapter = adapterNextView
        userList?.forEach {
            adapterNextView.addNextUser(it)
        }
        val bm = (binding.imageView.drawable as BitmapDrawable).bitmap
        setImageViewColor(bm)

        }



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


