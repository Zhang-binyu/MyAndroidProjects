package com.example.todolist.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.data.TodoEntity
import com.example.todolist.ui.viewModel.TodoViewModel

@Composable
fun TodoItem(
    todoViewModel: TodoViewModel,
    paddingValues: PaddingValues,
) {
    val allTodos by todoViewModel.todoState.todos.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        content = {
            items(
                allTodos,
                key = { it.todoId }
            ){todo->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                        .clickable {
                            todoViewModel.updateTodoId(todo.todoId)
                            todoViewModel.updateTodoTitle(todo.todoTitle)
                            todoViewModel.updateEditingState(true)
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = todo.todoTitle,
                            fontSize = 24.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {todoViewModel.deleteTodo(todo)},
                        ) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        },
    )
}