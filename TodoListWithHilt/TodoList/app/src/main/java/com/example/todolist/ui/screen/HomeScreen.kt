package com.example.todolist.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.viewModel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    todoViewModel: TodoViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {todoViewModel.updateAddingState(true)}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 8.dp, start = 4.dp),
                title = { Text(text = "TodoList", fontSize = 32.sp)},
                colors = TopAppBarDefaults
                    .topAppBarColors(
                        titleContentColor = MaterialTheme
                            .colorScheme
                            .primary
                    )
            )
        }
    ) {paddingValues ->
        if (todoViewModel.todoState.isAddingTodo){
            AddTodoDialog(
                todoViewModel = todoViewModel
            )
        }

        if (todoViewModel.todoState.isEditingTodo){
            EditTodoDialog(
                todoViewModel = todoViewModel
            )
        }

        TodoItem(
            todoViewModel = todoViewModel,
            paddingValues = paddingValues
        )
    }
}