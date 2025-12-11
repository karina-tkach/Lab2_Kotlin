package com.example.lab2

import android.app.Application
import com.example.lab2.database.AppDatabase
import com.example.lab2.repository.TaskRepository

class MyApp  : Application() {
    private val appDb by lazy { AppDatabase.getDatabase(this) }
    val taskRepository by lazy { TaskRepository(appDb.appDao()) }
}