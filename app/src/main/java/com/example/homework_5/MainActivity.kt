package com.example.homework_5


import adapter.UserAdapter
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_5.databinding.ActivityMain3Binding
import dagger.hilt.android.AndroidEntryPoint
import dataclasses.UserService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMain3Binding
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var userService: UserService

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter.getInstance(this)
        binding.recyclerView.adapter = userAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        userService.getUsers(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("test", "${it.data}")
                userAdapter.setItems(it.data)
            }, {
                Toast.makeText(this, "Error loading users", Toast.LENGTH_SHORT).show()
            })

        binding.floatingActionButton.setOnClickListener {
            addUser()
        }
    }

    private fun addUser() {
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
Домашнее задание №12
RxJava

Задание 5. Работа с сетью
    Перепишите ДЗ9 с использованием RxJava

 */