package com.example.todo.data

enum class Priority {
    LOW, NORMAL, HIGH;
    
    fun getDisplayName(): String {
        return when (this) {
            LOW -> "Низкий"
            NORMAL -> "Обычный"
            HIGH -> "Высокий"
        }
    }
    
    companion object {
        fun fromDisplayName(value: String): Priority {
            return when (value) {
                "Низкий" -> LOW
                "Высокий" -> HIGH
                else -> NORMAL
            }
        }
    }
} 