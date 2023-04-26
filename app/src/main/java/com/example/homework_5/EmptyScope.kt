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
    val job = launch(start = CoroutineStart.LAZY) {
        println("Coroutine started")
        delay(1000)
        println("Coroutine finished")
    }

    println("Main thread continues to execute")
    delay(2000)

    job.start()
    job.join()
}
/*
a) Создайте несколько Корутин при помощи билдера launch и посмотрите,
как они себя ведут в рамках родительской Корутины,
а также залогируйте выполнение и используйте join.
b) Создайте несколько Корутин при помощи билдера async и
получайте результаты выполнения при помощи await.
Реализуйте отложенный старт корутины.
*/

