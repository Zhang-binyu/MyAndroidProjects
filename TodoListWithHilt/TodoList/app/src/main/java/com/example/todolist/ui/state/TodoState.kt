package com.example.todolist.ui.state

import com.example.todolist.data.TodoEntity
import kotlinx.coroutines.flow.Flow

data class TodoState(
    val todoId:Int = 0,
    val todoTitle:String = "",
    val isAddingTodo:Boolean = false,
    val isEditingTodo:Boolean = false,
    val todos:Flow<List<TodoEntity>>
)
