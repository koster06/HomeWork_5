package services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "MBR"
    }

    override fun onReceive(context: Context, intent: Intent) {

        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d(TAG, "111")
                // TODO: Ваш код обработки события
            }
            Intent.ACTION_SCREEN_ON -> {
                Log.d(TAG, "222")
                // TODO: Ваш код обработки события
            }
            Intent.ACTION_SCREEN_OFF -> {
                Log.d(TAG, "333")
                // TODO: Ваш код обработки события
            }

            else -> {
                Log.d(TAG, "444")
                // TODO: Ваш код обработки неизвестного события
            }
        }
    }
}
