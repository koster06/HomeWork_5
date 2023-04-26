package com.example.homework_5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

fun main() = runBlocking<Unit> {
    val flow = flow {
        for (i in 1..5) {
            emit(i)
        }
    }

    flow.collect {
        println("Received value: $it")
    }
}

/*
a) Создайте Flow, который не принимает никаких параметров и выводит в консоль получение значения.
b) Создайте Flow, который принимает что-то в свои параметры, и используйте это при генерации данных
(например, передайте число, которое будет множителем для генерируемых чисел).
*/

