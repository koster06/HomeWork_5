package com.example.homework_5

import adapters.ProductsAdapter
import adapters.UsersAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    lateinit var adapterProducts : ProductsAdapter
    lateinit var adapterUsers : UsersAdapter
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
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

/* Retrofit section */

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(REQRES).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        adapterProducts = ProductsAdapter()
        adapterUsers = UsersAdapter()
        binding.apply {
            recyclerConteiner.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerConteiner.adapter = adapterUsers
        }

        val userService = retrofit.create(UserService::class.java)
        val page = 2

        CoroutineScope(Dispatchers.IO).launch {
            //val list = userService.getAllProducts()
            val listUsers = userService.getUsers(page)
            runOnUiThread{
                adapterUsers.submitList(listUsers.data)
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
            .addToBackStack(null)
            .commit()
        Toast.makeText(this, "To Message Fragment", Toast.LENGTH_SHORT).show()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        return true
    }

    companion object {
        const val REQRES = "https://reqres.in/api/"
        const val DUMMY = "https://dummyjson.com"
    }
}
// DESCRIPTION
//--------------------------------------------------------------------------------------------------

/*
Задание 10.1
На основе 3 задания из ДЗ 8 переписать логику работы приложения используя liveData и паттерн MVVM ++
*/