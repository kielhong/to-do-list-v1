package com.widehouse.todolist.item.dto

import com.widehouse.todolist.item.Todo
import com.widehouse.todolist.item.TodoStatus
import java.time.LocalDateTime

data class TodoRequest(
    val title: String,
    val status: TodoStatus = TodoStatus.TODO,
    val dueDate: LocalDateTime?
) {
    fun toEntity(): Todo {
        return Todo(
            title = title,
            status = status,
            dueDate = dueDate
        )
    }
}
