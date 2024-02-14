package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.todolist.roomApp.TodoRoomApplication
import com.example.todolist.ui.screen.HomeScreen
import com.example.todolist.ui.theme.TodoListTheme
import com.example.todolist.ui.viewModel.TodoViewModel

class MainActivity : ComponentActivity() {
    private val todoDao = TodoRoomApplication.todoRoomDatabase.todoDao()
    private val todoViewModel = TodoViewModel(todoDao = todoDao)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val todoState = todoViewModel.todoState.collectAsState()
                    HomeScreen(todoState = todoState.value, todoEvent = todoViewModel::onEvent)
                }
            }
        }
    }
}