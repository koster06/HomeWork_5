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
import io.reactivex.rxjava3.internal.util.BackpressureHelper.add
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        Observable.range(1, 10)
            .groupBy { if (it % 2 == 0) "Even" else "Odd" }                         /* groupBy */
            .flatMapSingle { group ->
                group.toList().map { list ->
                    val key = group.key ?: ""
                    val average = list.average()
                    "$key: ${list.joinToString()} Avg: $average"
                }
            }
            .subscribe { println(it) }
/*-------------------------------------------------------------------------------*/

        val observable = Observable.just(-1, 2, -3, 4, 5, -6, -7, 8, 9, -10)

        observable
            .groupBy { if (it >= 0) "Positive" else "Negative" }
            .subscribe { groupObservable ->
                groupObservable.toList()
                    .subscribe { list ->
                        println("Group: ${groupObservable.key} Values: $list")
                    }
            }
/*-------------------------------------------------------------------------------*/

        val source = Observable.just(-1, 2, 3, -4, -5, 6, 7, -8, 9)

        val positiveBuffer = source.filter { it > 0 }.buffer(3)           /* filter, buffer */
        val negativeBuffer = source.filter { it < 0 }.buffer(2)

        positiveBuffer.subscribe { println("Positive values: $it") }
        negativeBuffer.subscribe { println("Negative values: $it") }

/*---------------------------------------------------------------------------------------------*/

        val words = listOf("apple", "banana", "car", "dog", "elephant", "flower", "grape")
        val wordsObservable = Observable.fromIterable(words)
        val groupedWordsObservable = wordsObservable.groupBy { word ->
            if (word.length < 6) "short" else "long"
        }
        groupedWordsObservable.subscribe { group ->
            group.subscribe { word ->
                println("Group: ${group.key}, Word: $word")
            }
        }

/*---------------------------------------------------------------------------------------------*/

        val values = listOf(-2, -1, 1, 2, 3, -4, 4, -5, 5)

        Observable.fromIterable(values)                             /* groupBy, takeWhile */
            .groupBy { if (it >= 0) "positive" else "negative" }
            .switchMap { group ->
                group.takeWhile { it >= 0 }
            }
            .subscribe { println(it) }

/*---------------------------------------------------------------------------------------------*/

        val words1 = listOf("apple", "banana", "cat", "dog", "elephant", "stop", "frog", "goat")

        Observable.fromIterable(words1)
            .groupBy { it == "stop" }                           /* filter, groupBy, takeWhile */
            .flatMap { group ->
                if (group.key == true) {
                    group
                } else {
                    group.takeWhile { it.length < 6 }
                }
            }
            .filter { it != "stop" }
            .subscribe { println(it) }

/*---------------------------------------------------------------------------------------------*/



    }
}


/*------------------- Description----------------
Домашнее задание №12
RxJava
Задание 1. Операторы преобразования
    Примените все операторы для генерации ссылок и загрузки картинок в ImageView (нужно сгенерировать не менее двух ссылок). ++

Задание 2. Операторы объединения
    Примените все операторы. В рамках комбинирования используйте какую-либо арифметическую функцию (соответственно, потоки должны генерировать числа). ++

Задание 3. Операторы группирования и сортировки ++

    Примените операторы для сортировки и отсева значений по следующим критериями:
разделить положительные и отрицательные значения;
выводить слова, пока их длина менее 6 символов;
пропускать все отрицательные значения, пока не придет одно положительное;
выводить все слова, пока не придет слово stop.

Задание 4. Операторы для оптимизации
 	Создайте кнопку и текстовое поле. По нажатию на кнопку в нее должно записываться
 	инкрементируемое значение, а в текстовое поле должно записываться значение, прошедшее
 	через один из операторов оптимизации (выдержавшее какой-то интервал) .
 	Реализуйте задание для всех операторов.

Задание 5. Работа с сетью
    Перепишите ДЗ9 с использованием RxJava

 */