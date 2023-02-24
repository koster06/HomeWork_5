package com.example.homework_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.homework_5.databinding.ActivityMainBinding
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
            openFrag(it)
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

        //openFrag(Fragment4.newInstance(), R.id.place_holder)

    }// I dont want to do the first fragment into the second.... For my mind it so simple

    private fun openFrag (idFragment:Int) {
        var x:Fragment? = null
        when(idFragment) {
            1 -> x = Fragment1.newInstance()
            2 -> x = Fragment2.newInstance()
            3 -> x = Fragment3.newInstance()
            4 -> x = Fragment4.newInstance()
        }
        //if (x != null) {
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.place_holder, x!!)
                .commit()
        //}

    }
    /*
    Задание 3
Создать второй фрагмент  на Java -- может быть, но не сегодня, не в мою смену!
В первом фрагменте добавить FrameLayout -- он и так там был... ааааа, делать фрагмент внутри фрагмета!
Второй фрагмент вызвать из первого фрагмента ++
Передать во второй фрагмент параметры, переданные из активити ++
     */
    /*
    Задание 4
Создать еще минимум 2 фрагмента
Реализовать механизм смены фрагментов в активити,
по нажатию на разные кнопки активити
(для каждого фрагмента своя кнопка)
В центре каждого фрагмента разместить любую картинку
и изменить фон самого фрагмента
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