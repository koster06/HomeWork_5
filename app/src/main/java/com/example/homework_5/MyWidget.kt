package com.example.homework_5

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class MyWidget : AppWidgetProvider() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            GlobalScope.launch {
                val data = loadDataFromApi()
                val bitmap = data?.let { loadImageFromUrl(it.imageUrl) }
                updateAppWidget(context, appWidgetManager, appWidgetId, data?.name ?: "", bitmap)
            }
        }
    }

    private suspend fun loadDataFromApi(): UserData? = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        var inputStream: BufferedInputStream? = null

        try {
            val url = URL("https://reqres.in/api/users/2")
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = BufferedInputStream(connection.inputStream)
                val response = inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(response)
                val userDataJson = jsonObject.getJSONObject("data")
                val name = userDataJson.getString("first_name")
                val avatarUrl = userDataJson.getString("avatar")
                return@withContext UserData(name, avatarUrl)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            inputStream?.close()
        }
        return@withContext null
    }

    private suspend fun loadImageFromUrl(url: String): Bitmap? = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        var inputStream: BufferedInputStream? = null

        try {
            val imageUrl = URL(url)
            connection = imageUrl.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = BufferedInputStream(connection.inputStream)
                return@withContext BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            inputStream?.close()
        }
        return@withContext null
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        name: String,
        bitmap: Bitmap?
    ) {
        val views = RemoteViews(context.packageName, R.layout.my_widget)
        views.setTextViewText(R.id.textView, name)
        bitmap?.let { views.setImageViewBitmap(R.id.imageView, it) }

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    data class UserData(val name: String, val imageUrl: String)
}
