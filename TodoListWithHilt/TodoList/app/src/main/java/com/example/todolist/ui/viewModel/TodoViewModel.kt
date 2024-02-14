package com.example.todolist.ui.viewModel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TodoEntity
import com.example.todolist.data.TodoRepository
import com.example.todolist.ui.state.TodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
):ViewModel(){
    // define the state
    var todoState by mutableStateOf(
        TodoState(todos = todoRepository.getAllTodos())
    )

    // room database operation
    fun saveTodo(){
        val todoTitle = todoState.todoTitle

        if (todoTitle.isBlank()){
            return
        }

        val todo =TodoEntity(todoTitle = todoTitle)

        viewModelScope.launch {
            todoRepository.insertTodo(todoEntity = todo)
        }

        todoState = todoState.copy(
            todoTitle = "",
            isAddingTodo = false
        )
    }
    fun deleteTodo(todoEntity: TodoEntity){
        viewModelScope.launch {
            todoRepository.deleteTodo(todoEntity = todoEntity)
        }
    }
    fun updateTodo(){
        val todoId = todoState.todoId
        val todoTitle = todoState.todoTitle
        val todo = TodoEntity(todoId = todoId, todoTitle = todoTitle)

        viewModelScope.launch {
            todoRepository.updateTodo(todoEntity = todo)
        }

        todoState = todoState.copy(
            todoTitle = "",
            isEditingTodo = false
        )
    }

    fun updateAddingState(newValue:Boolean){
        todoState = todoState.copy(
            isAddingTodo = newValue
        )
    }
    fun updateTodoTitle(newValue:String){
        todoState = todoState.copy(
            todoTitle = newValue
        )
    }
    fun updateEditingState(newValue: Boolean){
        todoState = todoState.copy(
            isEditingTodo = newValue
        )
    }
    fun updateTodoId(newValue:Int){
        todoState = todoState.copy(
            todoId = newValue
        )
    }
}