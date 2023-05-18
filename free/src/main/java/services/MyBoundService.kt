package services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {

    companion object {
        private const val TAG = "MBS"
    }

    private var isStarted = false

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind")
        isStarted = true
        return MyBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        return true
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "onRebind")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        if (!isStarted) {
            Log.d(TAG, "Service started")
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    fun doSomething() {
        Log.d(TAG, "doSomething")
    }

    inner class MyBinder : Binder() {
        fun getService(): MyBoundService {
            return this@MyBoundService
        }
    }
}
