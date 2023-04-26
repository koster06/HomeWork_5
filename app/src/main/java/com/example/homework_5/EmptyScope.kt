package com.example.homework_5

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.CoroutineContext

class EmptyScope: CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job

    val sharedFlowWithBuffer = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 1) //с буфером в 1 элемент

    val sharedFlowOnlyBuffer = MutableSharedFlow<Int>(replay = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST) //только с буфером

    val sharedFlowWithCacheAndBuffer = MutableSharedFlow<Int>(replay = 10, extraBufferCapacity = 100, onBufferOverflow = BufferOverflow.DROP_LATEST)
//кэш на 10 и буфер на 100 эл.


}


/*
Создайте SharedFlow с различными настройками
(с буфером и кэшем, только с буфером, используйте различные стратегии при заполнении буфера).
*/

