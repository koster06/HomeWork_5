package com.example.homework_5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

suspend fun main() {
    val flow = flowOf(1, 2, 3, 4, 5)
    flow.flatMapConcat { number ->
        flow {
            emit(number)
            delay(100)
            emit(number * 2)
            delay(100)
            emit(number * 3)
        }
    }
        .collect { println(it) }
}

/*
Создайте три различных варианта Flow с промежуточными и терминальными операторами на ваш выбор.
*/

