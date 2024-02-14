package com.example.todolist.ui.state

import com.example.todolist.data.TodoEntity

data class TodoState(
    val todoId:Int = 0,
    val todoTitle:String = "",
    val isAddingTodo:Boolean = false,
    val isEditingTodo:Boolean = false,
    val todos:List<TodoEntity> = emptyList()
)
