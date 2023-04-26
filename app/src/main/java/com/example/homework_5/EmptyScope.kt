package com.example.homework_5

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job
}

fun main() = runBlocking {
    val unbufferedChannel = Channel<Int>()     // unbufferedChannel
    val job1 = launch {
        unbufferedChannel.send(1)
        println("Sent 1")
        delay(500)
        unbufferedChannel.send(2)
        println("Sent 2")
    }
    val job2 = launch {
        delay(1000)
        println("Received ${unbufferedChannel.receive()}")
        delay(500)
        println("Received ${unbufferedChannel.receive()}")
    }
    job1.join()
    job2.join()
    println()

    val bufferedChannel = Channel<Int>(1) //bufferedChannel
    val job3 = launch {
        bufferedChannel.send(1)
        println("Sent 1")
        delay(500)
        bufferedChannel.send(2)
        println("Sent 2")
    }
    val job4 = launch {
        delay(1000)
        println("Received ${bufferedChannel.receive()}")
        delay(500)
        println("Received ${bufferedChannel.receive()}")
    }
    job3.join()
    job4.join()
    println()

    val conflatedChannel = ConflatedBroadcastChannel<Int>() //conflatedChannel
    val job5 = launch {
        conflatedChannel.send(1)
        println("Sent 1")
        delay(500)
        conflatedChannel.send(2)
        println("Sent 2")
    }
    val job6 = launch {
        delay(1000)
        println("Received ${conflatedChannel.value}")
        delay(500)
        println("Received ${conflatedChannel.value}")
    }
    job5.join()
    job6.join()
    println()

    // Broadcast channel example
    val broadcastChannel = BroadcastChannel<Int>(1)
    val job7 = launch {
        broadcastChannel.send(1)
        println("Sent 1")
        delay(500)
        broadcastChannel.send(2)
        println("Sent 2")
    }
    val job8 = launch {
        delay(1000)
        println("Received ${broadcastChannel.openSubscription().receive()}")
        delay(500)
        println("Received ${broadcastChannel.openSubscription().receive()}")
    }
    job7.join()
    job8.join()
}

/*
Используйте различные варианты создания Корутин: coroutineScope, withContext, runBlocking.
*/

