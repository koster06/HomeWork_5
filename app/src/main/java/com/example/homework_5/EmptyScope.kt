package com.example.homework_5

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

class JobScope: CoroutineScope {
    private val parentJob = Job()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception: $throwable")
    }
    override val coroutineContext: CoroutineContext = parentJob + Dispatchers.Default + coroutineExceptionHandler
}

class DispatcherScope(dispatcher: CoroutineDispatcher): CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job + dispatcher
}

fun main() = runBlocking {
    val parentJob = Job() // parent Job
    val parentScope = CoroutineScope(Dispatchers.Default + parentJob)

    // launch
    repeat(3) {
        parentScope.launch {
            println("Coroutine $it started")
            delay(1000)
            println("Coroutine $it finished")
        }
    }

    // async
    val deferredList = List(3) {
        parentScope.async {
            println("Async $it started")
            delay(1000)
            println("Async $it finished")
            "Result $it" // return result
        }
    }

    // join
    parentScope.launch {
        println("Waiting for coroutines to finish...")
        deferredList.forEach { it.join() }
        println("All coroutines finished")
    }

    println("Main thread continues to execute")

    delay(3000)
    parentJob.cancel() // cancelling parent Job
}
/*
a) Создайте несколько Корутин при помощи билдера launch и посмотрите,
как они себя ведут в рамках родительской Корутины,
а также залогируйте выполнение и используйте join.
b) Создайте несколько Корутин при помощи билдера async и
получайте результаты выполнения при помощи await.
Реализуйте отложенный старт корутины.
*/

