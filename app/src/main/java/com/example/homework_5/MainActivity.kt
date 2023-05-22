package com.example.homework_5

import adapter.UserAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import applications.appModule
import com.example.homework_5.databinding.ActivityMain3Binding
import com.google.firebase.perf.metrics.AddTrace
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import viewmodels.UserViewModel
import viewmodels.UserViewModelFactory

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMain3Binding
    lateinit var userAdapter: UserAdapter
    private val viewModelFactory: UserViewModelFactory by inject()

    @SuppressLint("SetTextI18n")
    @AddTrace(name = "onCreateTrace", enabled = true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }
    }
    fun addUser(){
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