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

fun main() {
    val emptyScope = EmptyScope()
    val jobScope = JobScope()
    val dispatcherScope = DispatcherScope(Dispatchers.IO)

    emptyScope.launch {
        println("Empty scope: $coroutineContext")
    }

    jobScope.launch {
        println("Job scope: $coroutineContext")
        launch {
            println("Child coroutine: $coroutineContext")
        }
    }

    dispatcherScope.launch {
        println("Dispatcher scope: $coroutineContext")
    }

    Thread.sleep(1000)
}
/*
Создайте Scope с различным набором входных параметров
(с пустым контекстом, с Job, с Dispatcher) и создайте корутины в этом Scope,
залогируйте объекты контекста, чтобы проверить, что будет передаваться.
Залогируйте вложенную передачу контекста между Корутинами.
*/

