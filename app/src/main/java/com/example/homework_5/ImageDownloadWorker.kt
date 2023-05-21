package com.example.homework_5

import android.content.Context
import android.os.Build
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.squareup.picasso.Picasso
import java.time.Duration
import kotlin.math.pow

class ImageDownloadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {

        inputData.getString(KEY_IMAGE_URL)

        val isImageDownloaded = downloadImage()

        return if (isImageDownloaded) {
            Result.success()
        } else {
            calculateRetryDelay(3)
            val retryWorkRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                OneTimeWorkRequestBuilder<ImageDownloadWorker>()
                    .setConstraints(createConstraints())
                    .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofSeconds(2))
                    .build()
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            WorkManager.getInstance(applicationContext)
                .enqueueUniqueWork(IMAGE_DOWNLOAD_WORK_NAME, ExistingWorkPolicy.REPLACE, retryWorkRequest)

            Result.retry()
        }
    }

    private fun createConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
    }

    private fun downloadImage(): Boolean {
        return try {
            Picasso.get().load(KEY_IMAGE_URL).get()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    companion object {
        private const val KEY_IMAGE_URL = "https://www.pexels.com/photo/a-smartphone-beside-the-black-ceramic-coffee-cup-4927787/"
        private const val IMAGE_DOWNLOAD_WORK_NAME = "image_download_work"

        fun enqueueImageDownloadWork(context: Context, imageUrl: String) {
            val inputData = workDataOf(KEY_IMAGE_URL to imageUrl)
            val imageDownloadWorkRequest = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
                .setConstraints(createConstraints1())
                .setInputData(inputData)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(IMAGE_DOWNLOAD_WORK_NAME, ExistingWorkPolicy.REPLACE, imageDownloadWorkRequest)
        }

        private fun createConstraints1(): Constraints {
            return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED) // Требуется подключение к Wi-Fi
                .setRequiresCharging(true) // Требуется зарядка батареи
                .build()
        }
    }
    private fun calculateRetryDelay(retryCount: Int): Long {
        val initialDelayMillis = 1000L
        val maxDelayMillis = 60_000L

        val delayMillis = initialDelayMillis * (2.0.pow(retryCount.toDouble())).toLong()

        return minOf(delayMillis, maxDelayMillis)
    }


}
