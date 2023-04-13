package com.example.homework_5


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework_5.databinding.ActivityMain3Binding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.atomic.AtomicInteger
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
    private val atomicInt = AtomicInteger(0)



    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)


/*---------------------------------------------------------------------------------------------*/

        var counter = 0

        binding.button.setOnClickListener {
            counter++
            binding.button.text = counter.toString()
        }
        Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.textView.text = counter.toString()
            }

/*---------------------------------------------------------------------------------------------*/

        val textUpdater = PublishSubject.create<Unit>()
            textUpdater.debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { binding.textView2.text = atomicInt.incrementAndGet().toString() }

        binding.button2.setOnClickListener {
            val newValue = atomicInt.incrementAndGet()
            binding.button2.text = newValue.toString()
            textUpdater.onNext(Unit)
        }
/*---------------------------------------------------------------------------------------------*/



/*---------------------------------------------------------------------------------------------*/



    }
}


/*------------------- Description----------------
Домашнее задание №12
RxJava

Задание 4. Операторы для оптимизации
 	Создайте кнопку и текстовое поле. По нажатию на кнопку в нее должно записываться
 	инкрементируемое значение, а в текстовое поле должно записываться значение, прошедшее
 	через один из операторов оптимизации (выдержавшее какой-то интервал) .
 	Реализуйте задание для всех операторов.

Задание 5. Работа с сетью
    Перепишите ДЗ9 с использованием RxJava

 */