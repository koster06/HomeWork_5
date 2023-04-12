package com.example.homework_5


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

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMain3Binding
    private var disposable: Disposable? = null
    private val urls = listOf(
        "https://cdn.pixabay.com/photo/2015/11/16/16/28/bird-1045954_1280.jpg",
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg",
        "https://cdn.pixabay.com/photo/2014/09/14/18/04/dandelion-445228_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/04/18/22/05/seashells-1337565_1280.jpg"
    )
    val urlss = arrayOf(
        "https://cdn.pixabay.com/photo/2017/05/31/18/38/sea-2361247_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/03/17/14/46/red-tailed-black-cockatoo-7858776_1280.jpg",
        "https://cdn.pixabay.com/photo/2019/10/30/16/19/fox-4589927_1280.jpg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageViewList = listOf(
            with(binding) {
                imageView1
                imageView2
                imageView3
            }
        )

        val disposable1 = Observable.create<String> { emitter ->    /* CREATE */
            emitter.onNext(urls[0])                                 /* map */
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { url ->
                val drawable = Glide.with(this)
                    .load(url)
                    .submit()
                    .get()
                drawable
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { drawable ->
                binding.imageView.setImageDrawable(drawable)
            }
        Log.d("test", "1st ended")


        val disposable2 = Observable.just(urls)               /* JUST */
            .subscribeOn(Schedulers.io())                     /* map */
            .flatMapIterable { it }
            .map { url ->
                Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bitmap ->
                binding.imageView3.setImageBitmap(bitmap)
            }
        Log.d("test", "2nd ended")

        val observable1 = Observable.just(urls[1])         /* JUST but loading for 2 imageView */
            .subscribeOn(Schedulers.io())                  /* zip */
            .map { Glide.with(this).asBitmap().load(it).submit().get() }
            .observeOn(AndroidSchedulers.mainThread())

        val observable2 = Observable.just(urls[2])
            .subscribeOn(Schedulers.io())
            .map { Glide.with(this).asBitmap().load(it).submit().get() }
            .observeOn(AndroidSchedulers.mainThread())
        Log.d("test", "3rd starting")
        val disposable3 = Observable.zip(
            observable1,
            observable2, BiFunction<Bitmap, Bitmap, Pair<Bitmap, Bitmap>>
            { bitmap1, bitmap2 -> Pair(bitmap1, bitmap2)
        })
            .subscribe { pair ->
                binding.imageView4.setImageBitmap(pair.first)
                binding.imageView5.setImageBitmap(pair.second)
            }

        val obser = Observable.fromArray(*urlss)         /* FROMARRAY */
            .flatMap { url ->                            /* flatMap */
                Observable.just(url)
                    .subscribeOn(Schedulers.io())
                    .map {
                        Glide.with(this)
                            .load(url)
                            .submit()
                            .get()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bitmaps ->
                binding.imageView1.setImageDrawable(bitmaps[0])
                binding.imageView2.setImageDrawable(bitmaps[1])
            }

        val disposable4 = Observable.fromIterable(urls)         /* FROMITERABLE */
            .flatMap { url ->                                   /* flatMap */
                Observable.fromCallable {
                    Glide.with(this)
                        .asBitmap()
                        .load(url)
                        .submit()
                        .get()
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bitmap ->
                when (imageViewList.indexOfFirst { it.drawable == null }) {
                    else -> imageViewList.first { it.drawable == null }.setImageBitmap(bitmap)
                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
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