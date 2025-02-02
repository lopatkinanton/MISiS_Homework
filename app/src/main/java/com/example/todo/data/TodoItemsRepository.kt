package com.example.todo.data

import java.util.UUID

object TodoItemsRepository {
    private val todoList = mutableListOf<TodoItem>()

    init {
        todoList.addAll(
            listOf(
                TodoItem("1", "Купить продукты", Priority.NORMAL, null, false, System.currentTimeMillis(), null),
                TodoItem("2", "Закончить проект", Priority.HIGH, System.currentTimeMillis() + 86400000, false, System.currentTimeMillis(), null),
                TodoItem("3", "Позвонить маме", Priority.LOW, null, true, System.currentTimeMillis(), null)
            )
        )
    }

    fun getAllItems(): List<TodoItem> = todoList

    fun addItem(item: TodoItem) {
        todoList.add(item)
    }

    fun updateItem(item: TodoItem) {
        val index = todoList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            todoList[index] = item
        }
    }

    fun deleteItem(id: String) {
        todoList.removeAll { it.id == id }
    }
}