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
import com.example.todolist.ui.event.TodoScreenEvent
import com.example.todolist.ui.state.TodoState

@Composable
fun EditTodoDialog(
    todoState: TodoState,
    todoEvent:(TodoScreenEvent) -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Edit", fontSize = 24.sp)
        },
        onDismissRequest = { todoEvent(TodoScreenEvent.HideEditingDialog) },
        text = {
            TextField(
                value = todoState.todoTitle,
                onValueChange = { todoEvent(TodoScreenEvent.SetTodoTitle(it)) }
            )
        },
        confirmButton = {
            IconButton(onClick = { todoEvent(TodoScreenEvent.UpdateTodo) }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    )
}