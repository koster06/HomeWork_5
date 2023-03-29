package com.example.homework_5

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import java.util.concurrent.TimeUnit
import kotlin.math.sqrt

class ActivityForHomework : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_homework)

        Observable.just("abc", "ab", "abcd")  //----11.1----
            .map { it.length * it.length }
            .subscribe { println(it) }

        Observable.just(1, 4, 15)  //----11.2----
            .filter { sqrt(it.toDouble()).toInt().toDouble() == sqrt(it.toDouble()) }
            .subscribe { println(it) }

        val hotObservable = PublishSubject.create<Int>().apply { //----11.4----
            onNext(1)
            onNext(2)
            onNext(3)
        }

        val coldObservable = hotObservable.publish().refCount()

        coldObservable.subscribe { println("Subscriber 1: $it") }
        coldObservable.subscribe { println("Subscriber 2: $it") }

        val replaySubject = ReplaySubject.create<String>()    //----11.5----

        replaySubject.onNext("Hello")
        replaySubject.onNext("World")
        replaySubject.subscribe { println("Subscriber 1: $it") }
        replaySubject.onNext("Goodbye")
        replaySubject.subscribe { println("Subscriber 2: $it") }
        replaySubject.onNext("ReactiveX")

        val myObservable = Observable.interval(1, TimeUnit.SECONDS) //----11.6----
            .publish()

        myObservable.connect()
        // Добавляем задержку в 5 секунд для того, чтобы набралось достаточно данных
        Thread.sleep(5000) // пропускаем 0,1,2,3,4
        // Подписываемся на Observable и выводим данные в консоль
        val observer = myObservable.subscribe { println("Subscriber 1: $it") } // 5,6,7
        // Добавляем задержку в 3 секунды, чтобы в это время не было активных подписчиков
        Thread.sleep(3000)
        // Отписываемся от Observable
        observer.dispose()
        // Добавляем задержку в 5 секунд, чтобы убедиться, что Observable все еще работает
        Thread.sleep(5000)
        // Подписываемся на Observable снова и выводим данные в консоль
        myObservable.subscribe { println("Subscriber 2: $it") } // 13,14,15,16...

        val observable = Observable.create<Int> {emitter -> //----11.7----
            run {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onNext(3)
                emitter.onComplete()
            }
        }.replay(2)

        observable.connect()

        observable.subscribe {println(it)}
        observable.subscribe {println(it)}

        Observable.create<Int> { emitter -> //----11.8.1----Observable с одним потоком на создание и выполнение событий, логирование с помощью observeOn и subscribeOn
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.single())
            .doOnNext { println("Thread: ${Thread.currentThread().name}, item: $it") }
            .subscribe()

        Observable.create<Int> { emitter -> //----11.8.2----Observable с использованием io() Scheduler-а, логирование с помощью observeOn и subscribeOn
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnNext { println("Thread: ${Thread.currentThread().name}, item: $it") }
            .subscribe()

        Observable.create<Int> { emitter -> //----11.8.3----Observable с использованием computation() Scheduler-а для выполнения сложных вычислений, логирование с помощью observeOn и subscribeOn
            for (i in 1..10) {
                val result = fibonacci(i)
                emitter.onNext(result)
            }
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .doOnNext { println("Thread: ${Thread.currentThread().name}, item: $it") }
            .subscribe()

        Observable.create<Int> { emitter -> //----11.8.4----Observable с использованием newThread() Scheduler-а для выполнения событий в отдельном потоке, логирование с помощью observeOn и subscribeOn
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .doOnNext { println("Thread: ${Thread.currentThread().name}, item: $it") }
            .subscribe()


        val source = Observable.just("apple", "banana", "pear", "grape", "orange")

        source.filter { !it.contains('r', true) } // Фильтруем слова с буквой "р"
            .map { it.toUpperCase() } // Преобразуем все слова в верхний регистр
            .subscribe { println(it) } // Выводим результат в консоль




    }

    private fun fibonacci(n: Int): Int {
        return if (n <= 2) 1 else fibonacci(n - 1) + fibonacci(n - 2)
    }
}
/*
11.1
Измените код примера так, чтобы в результате каждый элемент
представлял собой длину строки в квадрате:
Observable.just("abc", "ab", "abcd").subscribe {println(it)}

11.2
Допишите данный оператор фильтрации так, чтобы он возвращал числа,
у которых извлекаемый корень принадлежит множеству целых чисел:
Observable.just(1, 4, 15)
    .filter { }
    .subscribe { println(it)}

11.3
Реализуйте Observable, который оборачивает клики на кнопку.
Подсказка: Используйте оператор create на основе Emitter-а или Observer-а.

11.4
Дан горячий Observable, трансформируйте его в холодный:
    PublishSubject.create<Int> {
    it.onNext(1)
    it.onNext(2)
    it.onNext(3)
    }

11.5
Создайте Subject с историей всех сообщений.
Каждый новый подписчик должен видеть всю историю с самого начала.

11.6
В нижеприведенном коде объявлен Observable.
Трансформируйте его в hot Observable и подготовьте его к отправке данных,
вне зависимости от того, есть ли у него подписчики или нет:
val myObservable = Observable.interval(1, TimeUnit.SECONDS)

11.7
Дан Observable, трансформируйте его таким образом, чтобы каждый новый подписчик
получал всю последовательность заново:
val observable = Observable.create<Int> {
    emitter -> run {
        emitter.onNext(1)
        emitter.onNext(2)
        emitter.onNext(3)
        emitter.onNext.onComplete
        }
    } observable.subscribe {println(it)} observable.subscribe {println(it)}

11.8
Создайте различные варианты Observable при помощи Schedulers,
залогируйте выполнение, чтобы посмотреть на переключение и использования потоков.
(минимум 4)

11.9
Создайте Observable, генерирующий строки,
который будет отфильтровывать все слова с какой-то буквой
(например «р», независимо от регистра) и будет их все
трансформировать в верхний регистр (ТО ЕСТЬ ТАК).
 */