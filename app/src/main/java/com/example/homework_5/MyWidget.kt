package com.example.homework_5

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.widget.RemoteViews

class MyWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("test", "textColor.toString()")
        val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        if (intent.action == ACTION_WIDGET_CONFIGURE && appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            val launchApp = intent.getBooleanExtra(EXTRA_LAUNCH_APP, false)
            val transparentBackground = intent.getBooleanExtra(EXTRA_TRANSPARENT_BACKGROUND, false)
            val textSize = intent.getFloatExtra(EXTRA_TEXT_SIZE, 24f)
            val textColor = intent.getIntExtra(EXTRA_TEXT_COLOR, Color.BLACK)

            saveWidgetSettings(context,launchApp, appWidgetId,  transparentBackground, textSize, textColor)
            updateAppWidget(context,appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager:AppWidgetManager, appWidgetId: Int
    ) {

        val clickIntent = Intent(context, MyWidget::class.java).apply {
            action = ACTION_WIDGET_CLICK
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, clickIntent, PendingIntent.FLAG_IMMUTABLE)
        Log.d("test", "textSize.toString()")
        // Загрузка сохраненных настроек виджета
        val settings = loadWidgetSettings(context, appWidgetId)

        // Создание RemoteViews и настройка внешнего вида виджета
        val views = RemoteViews(context.packageName, R.layout.my_widget).apply {
            setOnClickPendingIntent(R.id.layout, pendingIntent)
            setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, settings.textSize)
            setTextColor(R.id.appwidget_text, settings.textColor)

            if (settings.transparentBackground) {
                // Установка прозрачного фона
                setInt(R.id.layout, "setBackgroundColor", Color.TRANSPARENT)
            } else {
                // Установка непрозрачного фона
                setInt(R.id.layout, "setBackgroundResource", R.drawable.app_widget_background)
            }
        }

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun saveWidgetSettings(
        context: Context,
        launchApp: Boolean,
        appWidgetId: Int,
        transparentBackground: Boolean,
        textSize: Float,
        textColor: Int
    ) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(PREF_LAUNCH_APP_PREFIX + appWidgetId, launchApp)
        editor.putBoolean(PREF_TRANSPARENT_BACKGROUND_PREFIX + appWidgetId, transparentBackground)
        editor.putFloat(PREF_TEXT_SIZE_PREFIX + appWidgetId, textSize)
        editor.putInt(PREF_TEXT_COLOR_PREFIX + appWidgetId, textColor)
        editor.apply()
        updateWidget(context)
    }

    private fun loadWidgetSettings(
        context: Context,
        appWidgetId: Int
    ):WidgetSettings {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val launchApp = prefs.getBoolean(PREF_LAUNCH_APP_PREFIX + appWidgetId, false)
        val transparentBackground = prefs.getBoolean(PREF_TRANSPARENT_BACKGROUND_PREFIX + appWidgetId, false)
        val textSize = prefs.getFloat(PREF_TEXT_SIZE_PREFIX + appWidgetId, 24f)
        val textColor = prefs.getInt(PREF_TEXT_COLOR_PREFIX + appWidgetId, Color.BLACK)
        Log.d("test", textColor.toString())
        return WidgetSettings(launchApp, transparentBackground,textSize,textColor)
    }

    private fun updateWidget(context: Context){
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(ComponentName(context,javaClass))
        ids.forEach { id -> updateAppWidget(context, manager, id) }
    }

    companion object {
        const val ACTION_WIDGET_CONFIGURE = "com.example.widget.ACTION_WIDGET_CONFIGURE"
        const val ACTION_WIDGET_CLICK = "com.example.widget.ACTION_WIDGET_CLICK"
        const val EXTRA_LAUNCH_APP = "extra_launch_app"
        const val EXTRA_TRANSPARENT_BACKGROUND = "extra_transparent_background"
        const val EXTRA_TEXT_SIZE = "extra_text_size"
        const val EXTRA_TEXT_COLOR = "extra_text_color"
        const val PREFS_NAME = "widget_prefs"
        const val PREF_LAUNCH_APP_PREFIX = "pref_launch_app_"
        const val PREF_TRANSPARENT_BACKGROUND_PREFIX = "pref_transparent_background_"
        const val PREF_TEXT_SIZE_PREFIX = "pref_text_size_"
        const val PREF_TEXT_COLOR_PREFIX = "pref_text_color_"
    }

}


//    override fun onReceive(context: Context?, intent: Intent?) {
//        super.onReceive(context, intent)
//
//        val action = intent!!.action?: ""
//        if (context!= null && action == "increase"){
//            val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
//            prefs.edit().putString(
//                "widgetText",
//                ((prefs.getString("widgetText", "0")?: "0").toInt() + 1).toString())
//                .apply()
//
//            updateWidget(context)
//        }
//    }
//    private fun String.pendingIntent(
//        context: Context
//    ):PendingIntent? {
//        val intent = Intent(context, this@MyWidget.javaClass)
//        intent.action = this
//        return PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
//    }

//    private fun updateAppWidget(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetId: Int
//    ) {
//        val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
//        val widgetText = prefs.getString("widgetText", "0")
//        // Construct the RemoteViews object
//        val views = RemoteViews(context.packageName, R.layout.my_widget)
//        views.setTextViewText(R.id.appwidget_text, widgetText)
//
//        views.setOnClickPendingIntent(R.id.appwidget_text, "increase".pendingIntent(context))
//        Log.d("test", "increase".pendingIntent(context).toString())
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views)
//    }
