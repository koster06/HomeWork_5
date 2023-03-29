package com.example.homework_5

import android.app.Application
import androidx.room.Room
import database.MyDatabase

class MyApplication : Application() {

    lateinit var database: MyDatabase

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java,
            "my-database"
        ).build()
    }
}
