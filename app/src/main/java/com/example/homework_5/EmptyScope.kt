package com.example.homework_5

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

fun main() = runBlocking {
    println("Main coroutine started")

    launch {
        println("Child coroutine started")
        delay(1000)
        println("Child coroutine finished")
    }

    println("Main coroutine finished")
}

/*
Используйте различные варианты создания Корутин: coroutineScope, withContext, runBlocking.
*/

