package com.example.homework_5

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews

class MyWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val action = intent!!.action?: ""
        if (context!= null && action == "increase"){
            val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            prefs.edit().putString(
                "widgetText",
                ((prefs.getString("widgetText", "0")?: "0").toInt() + 1).toString())
                .apply()

            updateWidget(context)
        }
    }
    private fun String.pendingIntent(
        context: Context
    ):PendingIntent? {
        val intent = Intent(context, this@MyWidget.javaClass)
        intent.action = this
        return PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val widgetText = prefs.getString("widgetText", "0")
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.my_widget)
        views.setTextViewText(R.id.appwidget_text, widgetText)

        views.setOnClickPendingIntent(R.id.appwidget_text, "increase".pendingIntent(context))
        Log.d("test", "increase".pendingIntent(context).toString())
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun updateWidget(context: Context){
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(ComponentName(context,javaClass))
        ids.forEach { id -> updateAppWidget(context, manager, id) }
    }

}
