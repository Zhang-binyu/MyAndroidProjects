package com.example.todolist.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.viewModel.TodoViewModel

@Composable
fun EditTodoDialog(
    todoViewModel: TodoViewModel
) {
    AlertDialog(
        title = {
            Text(text = "Edit", fontSize = 24.sp)
        },
        onDismissRequest = {
            todoViewModel.updateEditingState(false)
            todoViewModel.updateTodoTitle("")
        },
        text = {
            TextField(
                value = todoViewModel.todoState.todoTitle,
                onValueChange = {todoViewModel.updateTodoTitle(it)}
            )
        },
        confirmButton = {
            IconButton(onClick = {todoViewModel.updateTodo()}) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    )
}