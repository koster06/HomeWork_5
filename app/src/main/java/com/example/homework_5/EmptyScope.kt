package com.example.homework_5

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

fun main() = runBlocking {
    println("Main coroutine started")

    withContext(Dispatchers.IO) {
        println("Coroutine with IO context started")
        delay(1000)
        println("Coroutine with IO context finished")
    }

    println("Main coroutine finished")
}

/*
Используйте различные варианты создания Корутин: coroutineScope, withContext, runBlocking.
*/

