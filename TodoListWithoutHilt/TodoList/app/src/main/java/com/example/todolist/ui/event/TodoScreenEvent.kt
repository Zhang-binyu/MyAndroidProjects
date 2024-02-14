package com.example.todolist.ui.event

import com.example.todolist.data.TodoEntity

sealed interface TodoScreenEvent {
    object ShowAddingDialog:TodoScreenEvent
    object HideAddingDialog:TodoScreenEvent

    object ShowEditingDialog:TodoScreenEvent
    object HideEditingDialog:TodoScreenEvent

    object SaveTodo:TodoScreenEvent
    data class DeleteTodo(val todoEntity: TodoEntity):TodoScreenEvent
    data class SetTodoTitle(val todoTitle:String):TodoScreenEvent
    object UpdateTodo:TodoScreenEvent
    data class SetTodoId(val todoId:Int):TodoScreenEvent
}