package com.example.homework_5

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup

class WidgetConfigActivity : Activity() {
    private var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var radioGroup: RadioGroup
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_config)

        setResult(RESULT_CANCELED)

        val extras = intent.extras
        if (extras != null) {
            widgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        radioGroup = findViewById(R.id.radioGroup)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            val launchApp = (selectedRadioButtonId == R.id.radioButton4)
            val transparentBackground = (selectedRadioButtonId == R.id.radioButton3)
            val textSize = when (selectedRadioButtonId) {
                R.id.radioButton -> 16f
                else -> 24f // Default text size
            }
            val textColor = if (selectedRadioButtonId == R.id.radioButton2) {
                Color.WHITE
            } else {
                Color.BLACK
            }

            val widgetIntent = Intent()
            widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            widgetIntent.putExtra("LAUNCH_APP", launchApp)
            widgetIntent.putExtra("TRANSPARENT_BACKGROUND", transparentBackground)
            widgetIntent.putExtra("TEXT_SIZE", textSize)
            widgetIntent.putExtra("TEXT_COLOR", textColor)

            setResult(RESULT_OK, widgetIntent)
            finish()
        }
    }
}

