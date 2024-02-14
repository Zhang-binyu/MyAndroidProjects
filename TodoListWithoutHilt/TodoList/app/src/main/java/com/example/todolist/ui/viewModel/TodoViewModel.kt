package com.example.todolist.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TodoDao
import com.example.todolist.data.TodoEntity
import com.example.todolist.ui.event.TodoScreenEvent
import com.example.todolist.ui.state.TodoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel(
    private val todoDao: TodoDao
) : ViewModel() {
    private val _todoState = MutableStateFlow(TodoState())
    private val _todos = todoDao.getAllTodos().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )
    val todoState = combine(_todoState, _todos){todoState,todos->
        todoState.copy(
            todos = todos
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        TodoState()
    )

    fun onEvent(todoEvent:TodoScreenEvent){
        when(todoEvent){
            is TodoScreenEvent.DeleteTodo -> {
                viewModelScope.launch {
                    todoDao.deleteTodo(todoEvent.todoEntity)
                }
            }
            TodoScreenEvent.HideAddingDialog -> {
                _todoState.update { it.copy(
                    isAddingTodo = false,
                    todoTitle = ""
                ) }
            }
            TodoScreenEvent.SaveTodo -> {
                val todoTitle = todoState.value.todoTitle

                if (todoTitle.isBlank()){
                    return
                }

                val todo = TodoEntity(todoTitle = todoTitle)

                viewModelScope.launch {
                    todoDao.insertTodo(todoEntity = todo)
                }

                _todoState.update{it.copy(
                    todoTitle = "",
                    isAddingTodo = false
                )}
            }
            is TodoScreenEvent.SetTodoTitle -> {
                _todoState.update { it.copy(
                    todoTitle = todoEvent.todoTitle
                ) }
            }
            TodoScreenEvent.ShowAddingDialog -> {
                _todoState.update { it.copy(
                    isAddingTodo = true
                ) }
            }

            TodoScreenEvent.UpdateTodo -> {
                val todoId = todoState.value.todoId
                val todoTitle = todoState.value.todoTitle
                val todo = TodoEntity(todoId = todoId, todoTitle = todoTitle)

                viewModelScope.launch {
                    todoDao.updateTodo(todoEntity = todo)
                }
                _todoState.update { it.copy(
                    todoTitle = "",
                    isEditingTodo = false
                ) }
            }

            TodoScreenEvent.HideEditingDialog -> {
                _todoState.update { it.copy(
                    isEditingTodo = false,
                    todoTitle = ""
                ) }
            }

            TodoScreenEvent.ShowEditingDialog -> {
                _todoState.update { it.copy(
                    isEditingTodo = true
                ) }
            }

            is TodoScreenEvent.SetTodoId -> {
                _todoState.update { it.copy(
                    todoId = todoEvent.todoId
                ) }
            }
        }
    }
}