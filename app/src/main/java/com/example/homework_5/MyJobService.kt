package com.example.homework_5

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {

    private val TAG1 = "MyJobService"

    override fun onStartJob(params: JobParameters): Boolean {

        Log.d(TAG1, "Работа сервиса запущена")

        return false
    }

    override fun onStopJob(params: JobParameters): Boolean {

        Log.d(TAG1, "Работа сервиса отменена")

        return true
    }
}
