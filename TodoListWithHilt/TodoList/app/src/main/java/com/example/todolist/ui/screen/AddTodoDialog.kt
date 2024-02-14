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
fun AddTodoDialog(
    todoViewModel: TodoViewModel
) {
    AlertDialog(
        title = {
            Text(text = "Add", fontSize = 24.sp)
        },
        onDismissRequest = {
            todoViewModel.updateAddingState(false)
            todoViewModel.updateTodoTitle("")
        },
        text = {
            TextField(
                value = todoViewModel.todoState.todoTitle,
                onValueChange = {todoViewModel.updateTodoTitle(it)},
                placeholder = {
                    Text(text = "Task title.", fontSize = 24.sp)
                }
            )
        },
        confirmButton = {
            IconButton(onClick = {todoViewModel.saveTodo()}) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    )
}