package com.example.todo.data

import java.util.*

data class TodoItem(
    val id: String,
    val text: String,
    val priority: Priority,
    val deadline: Long?,
    var isDone: Boolean,
    val createdAt: Long,
    var updatedAt: Long?
)


enum class Priority {
    LOW, NORMAL, HIGH
}