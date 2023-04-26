package com.example.homework_5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

suspend fun main() {
    val numbers = listOf(1, 2, 3, 4, 5) // какая же это тоска решать такие задачи...
    val flow = flow {
        for (number in numbers) {
            emit(number)
        }
    }

    flow.map { it * 2 } //мапим
        .filter { it > 5 } //фильтруем больше 5
        .toList() // в список
        .also { println(it) }
}

/*
Создайте три различных варианта Flow с промежуточными и терминальными операторами на ваш выбор.
*/

