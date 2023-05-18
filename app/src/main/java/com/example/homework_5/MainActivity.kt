package com.example.homework_5

import adapter.UserAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import applications.appModule
import com.example.homework_5.databinding.ActivityMain3Binding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.metrics.AddTrace
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import viewmodels.UserViewModel
import viewmodels.UserViewModelFactory

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMain3Binding
    private lateinit var userAdapter: UserAdapter
    private val viewModelFactory: UserViewModelFactory by inject()
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var remoteConfig: FirebaseRemoteConfig

    @SuppressLint("SetTextI18n")
    @AddTrace(name = "onCreateTrace", enabled = true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        analytics = Firebase.analytics
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val debugMode = remoteConfig.getBoolean("debug_mode")
                if (debugMode) {
                    Log.d("TAG", "Режим отладки включен")
                } else {
                    Log.d("TAG", "Режим отладки выключен")
                }
            } else {
                Log.e("TAG", "Ошибка получения и активации параметров Remote Config", task.exception)
            }
        }

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        userAdapter = UserAdapter.getInstance(this)
        binding.recyclerView.adapter = userAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val viewModel = ViewModelProvider(this, viewModelFactory) [UserViewModel::class.java]
        viewModel.users.observe(this) { users ->
            userAdapter.setItems(users)
        }

        binding.floatingActionButton.setOnClickListener {
            addUser()
        }
binding.crashButton.setOnClickListener {
    analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
        param(FirebaseAnalytics.Param.ITEM_ID, "id")
        param(FirebaseAnalytics.Param.ITEM_NAME, "button")
        param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
    }
}
    }
    private fun addUser(){
        val fragment = FragmentNewUser()
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.fade_in,
                R.anim.fade_out
            )
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
    @AddTrace(name = "onItemClick", enabled = true)
    override fun onItemClick(userId: Int) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val debugMode = remoteConfig.getBoolean("debug_mode")
                    if (debugMode) {
                        Log.d("TAG", "onItemClick$userId")
                    } else {
                        Log.d("TAG", "Режим отладки выключен")
                    }
                } else {
                    Log.e("TAG", "Ошибка получения и активации параметров Remote Config", task.exception)
                }
            }
        val fragment = FragmentUserDetails()
        val args = Bundle()
        args.putInt("userId", userId)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
    }

}


/*------------------- Description----------------
Домашнее задание №16
Реализуйте многомодульность на основе ДЗ 15.
В комментарии опишите по какому принципу вы
разбивали Ваше приложение на модули.
Опишите связь ваших модулей
 */