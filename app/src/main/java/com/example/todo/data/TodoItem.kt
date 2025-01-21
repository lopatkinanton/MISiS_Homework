package com.example.todo.data

import java.util.*

data class TodoItem(
    val id: String,
    val text: String,
    val priority: Priority,
    val deadline: Long?,
    var isCompleted: Boolean,
    val createdAt: Long,
    var updatedAt: Long?
)