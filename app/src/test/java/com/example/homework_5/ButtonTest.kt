package com.example.homework_5

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ButtonTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testButtonClicked_launchesFragment() {
        val activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            val button = activity.findViewById<View>(R.id.button)

            // Нажимаем на кнопку
            button.performClick()

            // Проверяем, что фрагмент запущен
            val fragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (fragment != null) {
                assertEquals(FragmentNewUser::class.java, fragment.javaClass)
            }
        }
    }
}

