package com.example.todolist.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.TodoDao
import com.example.todolist.data.TodoDatabase
import com.example.todolist.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule{
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext context:Context):TodoDatabase{
        return Room.databaseBuilder(
            context = context,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase):TodoDao = todoDatabase.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao):TodoRepository = TodoRepository(todoDao = todoDao)
}