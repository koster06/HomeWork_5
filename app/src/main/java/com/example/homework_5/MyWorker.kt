package com.example.homework_5

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val TAG = "MyWorker"

    override fun doWork(): Result {

        val isConnectedToWifi = checkWifiConnection()

        val isBatteryLow = checkBatteryLevel()

        if (isConnectedToWifi && isBatteryLow) {
            Log.d(TAG, "Выполняется задача при подключенном Wi-Fi и низком заряде батареи")

        } else {
            Log.d(TAG, "Задача не выполняется из-за условий")
        }

        return Result.success()
    }

    private fun checkWifiConnection(): Boolean {

        return true
    }

    private fun checkBatteryLevel(): Boolean {

        return true
    }
}
