package com.example.homework_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit.UserListResponse
import retrofit.UserService
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter : UserAdapter
    private var userListResponse: UserListResponse? = null
    private var userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MessageFragment.newInstance())
                //.commit()
            binding.navView.setCheckedItem(R.id.nav_message)
        }

        adapter = UserAdapter(object : AdapterListener{
            override fun removeUser(user: User) {      //this function more safe, cos I didn't have any issues with this
                val indexToDelete = adapter.userList.indexOfFirst { it.id == user.id }
                adapter.userList.removeAt(indexToDelete)
                adapter.notifyDataSetChanged()
            }
        })

/* Retrofit section */

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService = retrofit.create(UserService::class.java)
        val page = 2

        CoroutineScope(Dispatchers.IO).launch {
            val response = userService.getUsers(page)
            if (response.isSuccessful) {
                Log.i("test", "response code: ${response.code()}")
                userListResponse = response.body()!!
                userList = userListResponse!!.data as MutableList<User>
                CoroutineScope(Dispatchers.Main).launch {
                    binding.apply {
                        recyclerConteiner.layoutManager = GridLayoutManager(this@MainActivity, 1)
                        recyclerConteiner.adapter = adapter
                        Log.i("test","${recyclerConteiner.isActivated}")
                        Log.i("test", "init ${userList.size}")
                        userList.forEach {
                            adapter.addUser(it)
                        }
                    }
                }
                Log.i("test", "userList size: ${userList.size}")
            } else {
                Log.i("test","Error: ${response.code()} \n ${response.message()}")
            }
        }
    }

// functions
//--------------------------------------------------------------------------------------------------

    override fun onBackPressed() {
        with(binding) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_message) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MessageFragment.newInstance())
            .commit()
        Toast.makeText(this, "To Message Fragment", Toast.LENGTH_SHORT).show()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        return true
    }
}
// DESCRIPTION
//--------------------------------------------------------------------------------------------------

/*
Задание 1
Сделать загрузку списка с помощью Retrofit в RecyclerView на главном экране приложения. (обязательно должна быть картинка в списке)
Дизайн должен соответствовать material и теме приложения
Помните про принципы DTO, POJO

*/