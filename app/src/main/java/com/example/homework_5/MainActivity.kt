package com.example.homework_5


import adapter.UserAdapter
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_5.databinding.ActivityMain3Binding
import dataclasses.User
import dataclasses.UserService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMain3Binding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private val userService by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter.getInstance(this)
        binding.recyclerView.adapter = userAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        viewModel.users.observe(this) { users ->
            userAdapter.setItems(users)
        }

        binding.floatingActionButton.setOnClickListener {
            addUser()
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
Домашнее задание №13



 */