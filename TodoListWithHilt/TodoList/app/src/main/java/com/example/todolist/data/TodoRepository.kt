package com.example.todolist.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val todoDao: TodoDao
) {
    fun getAllTodos(): Flow<List<TodoEntity>> = todoDao.getAllTodos()
    suspend fun insertTodo(todoEntity: TodoEntity) = todoDao.insertTodo(todoEntity = todoEntity)
    suspend fun deleteTodo(todoEntity: TodoEntity) = todoDao.deleteTodo(todoEntity = todoEntity)
    suspend fun updateTodo(todoEntity: TodoEntity) = todoDao.updateTodo(todoEntity = todoEntity)
}