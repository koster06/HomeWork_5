package com.example.homework_5

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.take
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

suspend fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        var i = 0
        while (true) {
            delay(100)
            channel.send(i++)
        }
    }
    val flow = channel.consumeAsFlow()
    flow.take(5)
        .collect { println(it) }
}

/*
Создайте три различных варианта Flow с промежуточными и терминальными операторами на ваш выбор.
*/

