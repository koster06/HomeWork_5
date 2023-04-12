package com.example.homework_5


import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.homework_5.databinding.ActivityMain3Binding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val observable5 = Observable.range(1, 5)                        /* concat */
        val observable6 = Observable.range(6, 5)
        val concatObservable = Observable.concat(observable5, observable6)

        val combinedObservable = concatObservable
            .reduce { acc: Int, value: Int -> acc + value }                         /* reduce */
        combinedObservable.subscribe { value ->
            binding.textView.text = "Sum of all numbers: $value"
        }
/*---------------------------------------------------------------------------------------------*/

        val observable1 = Observable.intervalRange(1, 5, 0, 1, TimeUnit.SECONDS)
            .map { it.toInt() }
            .flatMap { Observable.just(it).delay((Math.random() * 3000).toLong(), TimeUnit.MILLISECONDS) }

        val observable2 = Observable.intervalRange(1, 5, 0, 1, TimeUnit.SECONDS)
            .map { it.toInt() }
            .flatMap { Observable.just(it).delay((Math.random() * 3000).toLong(), TimeUnit.MILLISECONDS) }

        Observable.merge(observable1, observable2)                                   /* merge */
            .buffer(2)
            .filter { it.size == 2 }
            .map { it.maxOrNull()!! }
            .subscribe { binding.textView2.text = "Merged item: $it" }

/*---------------------------------------------------------------------------------------------*/

        val observable3 = Observable.just(1, 2, 3)
        val observable4 = Observable.just(10, 20, 30)

        Observable.zip(observable3, observable4) { t1: Int, t2: Int ->                  /* zip */
            t1 * t2
        }
            .subscribe { println(it) }
/*---------------------------------------------------------------------------------------------*/

        val numbers1 = Observable.just(1, 2, 3)
        val numbers2 = Observable.just(4, 5, 6)
        val numbers3 = Observable.just(7, 8, 9)

        Observable.combineLatest(numbers1, numbers2, numbers3) { n1, n2, n3 ->   /* combineLatest */
            n1 * n2 + n3
        }.subscribe { result ->
            println("Result: $result")
        }

/*---------------------------------------------------------------------------------------------*/

        val firstObservable = Observable.just("Здарова", "Ku-Ku", "Hola")
        val secondObservable = Observable.just("Zina", "musjo", "Artifiction")

        firstObservable.join(secondObservable,                                  /* join */
            { Observable.timer(2, TimeUnit.SECONDS) },
            { Observable.timer(1, TimeUnit.SECONDS) },
            { s1, s2 -> "$s1 $s2" })
            .subscribe { s -> println(s) }

/*---------------------------------------------------------------------------------------------*/

        val first = Observable.interval(1, TimeUnit.SECONDS)
            .map { "First: $it" }
            .take(3)

        val second = Observable.interval(2, TimeUnit.SECONDS)
            .map { "Second: $it" }
            .take(2)

        val third = Observable.interval(3, TimeUnit.SECONDS)
            .map { "Third: $it" }
            .take(1)

        val sources = listOf(first, second, third)

        Observable.interval(1, TimeUnit.SECONDS)
            .take(10)
            .switchMap { sources[it.toInt() % sources.size] }                   /* switchMap */
            .subscribe { println(it) }

/*---------------------------------------------------------------------------------------------*/

        val source1 = Observable.interval(1, TimeUnit.SECONDS)
            .map { "Source 1: $it" }
        val source2 = Observable.interval(2, TimeUnit.SECONDS)
            .map { "Source 2: $it" }
        val source3 = Observable.interval(3, TimeUnit.SECONDS)
            .map { "Source 3: $it" }

        Observable.amb(listOf(source1, source2, source3))                           /* amb */
            .take(5)
            .subscribe { println(it) }


    }
}


/*------------------- Description----------------
Домашнее задание №12
RxJava
Задание 1. Операторы преобразования
    Примените все операторы для генерации ссылок и загрузки картинок в ImageView (нужно сгенерировать не менее двух ссылок). ++

Задание 2. Операторы объединения
    Примените все операторы. В рамках комбинирования используйте какую-либо арифметическую функцию (соответственно, потоки должны генерировать числа).

Задание 3. Операторы группирования и сортировки

    Примените операторы для сортировки и отсева значений по следующим критериями:
разделить положительные и отрицательные значения;
выводить слова, пока их длина менее 6 символов;
пропускать все отрицательные значения, пока не придет одно положительное;
выводить все слова, пока не придет слово stop.

Задание 4. Операторы для оптимизации
 	Создайте кнопку и текстовое поле. По нажатию на кнопку в нее должно записываться инкрементируемое значение, а в текстовое поле должно записываться значение, прошедшее через один из операторов оптимизации (выдержавшее какой-то интервал) . Реализуйте задание для всех операторов.

Задание 5. Работа с сетью
    Перепишите ДЗ9 с использованием RxJava

 */