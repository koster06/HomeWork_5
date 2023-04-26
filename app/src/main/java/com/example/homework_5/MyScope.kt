package com.example.homework_5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyScope : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Default

    fun launchSuspend() = launch {
        println("Coroutine with suspend function started")
        delay(1000)
        println("Coroutine with suspend function finished")
    }

    fun launchNoSuspend() = launch {
        println("Coroutine without suspend function started")
        Thread.sleep(1000)
        println("Coroutine without suspend function finished")
    }

    fun cancelAll() {
        job.cancel()
        println("All coroutines cancelled")
    }
}

fun main() {
    val myScope = MyScope()

    myScope.launchSuspend()
    myScope.launchNoSuspend()

    Thread.sleep(500)
    myScope.cancelAll()
}

/*
Создайте свой скоуп. В рамках него создайте дочерние Корутины
как с вызовом suspend функций (например, delay),
так и не suspend функций. Реализуйте логику их отмены.
*/