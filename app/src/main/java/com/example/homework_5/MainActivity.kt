package com.example.homework_5

import android.content.Intent
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

        openFrag(1)

        shareModel.shareForActivity.observe(this, {
            openFrag(it)
        })

        val intent = Intent(this@MainActivity, MainActivity2::class.java)
        startActivity(intent)

        binding.button4.setOnClickListener {
            openFrag(1)
        }
        binding.button5.setOnClickListener {
            openFrag(2)
        }
        binding.button6.setOnClickListener {
            openFrag(3)
        }
        binding.button7.setOnClickListener {
            openFrag(4)
        }
        binding.button8.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)
        }


//        binding.bRight.setOnClickListener { // button for random changing Fragments
//                val start = 1               // I didn't want to lumber up the screen, there is a mess
//                val end = 4
//                openFrag( rand(start, end))
//        }

//        binding.bSender.setOnClickListener {
//            shareModel.shareForFragment1.value = binding.etSenderText.text.toString()
//            binding.etSenderText.text.clear()
//        }
//
//        binding.radioButton1.setOnClickListener {
//            shareModel.shareForFragment1Boolean.value = true
//        }
//        binding.radioButton2.setOnClickListener {
//            shareModel.shareForFragment1Boolean.value = false
//        }
//
//        binding.tvDateSender.setOnClickListener {
//            shareModel.shareForFragment1Date.value = myCalendar.time
//        }

    }// I didn't want to do the first fragment into the second.... For my mind it so simple

//    fun rand(start: Int, end: Int): Int {
//        require(start <= end) { "Illegal Argument" }
//        return (start..end).random()
//    }

    private fun openFrag (idFragment:Int) {
        var x:Fragment? = null
        when(idFragment) {
            1 -> x = Fragment1.newInstance()
            2 -> x = Fragment2.newInstance()
            3 -> x = Fragment3.newInstance()
            4 -> x = Fragment4.newInstance()
        }
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.place_holder, x!!)
                .commit()
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
Реализовать механизм смены фрагментов в активити, ++
по нажатию на разные кнопки активити
(для каждого фрагмента своя кнопка) ++
В центре каждого фрагмента разместить любую картинку ++
и изменить фон самого фрагмента ++
     */
    /*
    Задание 5
Изучить способы передачи данных между фрагментами
Реализовать минимум 2 способами следующее задание
(по желанию можно больше)
В идеале один из способов реализовать на Java

Задание 6

Создать новую активити на Java
Реализовать добавление 2 разных фрагментом в нее (фрагменты располагаются
вертикально друг за другом)
Добавить горизонтальную разметку к активити
(фрагменты располагаются горизонтально)
Реализовать следующую логику:
На первом фрагменте ввести текст в editText
При нажатии на кнопку, введенный текст должен
отобразиться во втором фрагменте
Реализовать логику сохранения состояния
при смене ориентации телефона
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