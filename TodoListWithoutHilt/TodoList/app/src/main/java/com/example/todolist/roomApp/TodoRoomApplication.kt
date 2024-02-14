package com.example.todolist.roomApp

import android.app.Application
import androidx.room.Room
import com.example.todolist.data.TodoDatabase

class TodoRoomApplication : Application() {
    companion object{
        lateinit var todoRoomDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoRoomDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todos"
        ).build()
    }
}