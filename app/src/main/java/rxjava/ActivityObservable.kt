package rxjava

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework_5.R
import io.reactivex.rxjava3.core.Observable

class ActivityObservable : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observable)

        Observable.just("abc", "ab", "abcd")// 11.1
            .map { it.length * it.length }
            .subscribe { println(it) }



    }
}

/*
Измените код примера так, чтобы в результате каждый элемент представлял собой
длину строки в квадрате: Observable.just("abc", "ab", "abcd").subscribe {println(it)}
 */