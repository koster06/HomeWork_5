package com.example.homework_5

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

fun main() {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught exception: $exception")
    }
    val parentJob = Job()
    val parentScope = CoroutineScope(parentJob + handler)

    parentScope.launch {
        println("Parent coroutine started")
        val childJob = Job(parent = coroutineContext[Job])
        val childScope = CoroutineScope(coroutineContext + childJob + handler)

        childScope.launch {
            println("Child coroutine started")
            val grandChildJob = Job(parent = coroutineContext[Job])
            val grandChildScope = CoroutineScope(coroutineContext + grandChildJob + handler)

            grandChildScope.launch {
                println("Grandchild coroutine started")
                throw Exception("Exception from grandchild coroutine")
            }
        }
    }

    Thread.sleep(5000)
    parentJob.cancel()
}

/*
Создайте иерархию Корутин и посмотрите, как будет передаваться эксепшен в этой цепочке.
*/

