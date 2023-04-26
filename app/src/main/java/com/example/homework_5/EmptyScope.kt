package com.example.homework_5

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

fun main() = runBlocking {
    println("Main coroutine started")

    val deferredResult = coroutineScope {
        async {
            println("Child coroutine started")
            delay(1000)
            println("Child coroutine finished")
            42
        }
    }

    println("Result is ${deferredResult.await()}")
    println("Main coroutine finished")
}

/*
Используйте различные варианты создания Корутин: coroutineScope, withContext, runBlocking.
*/

