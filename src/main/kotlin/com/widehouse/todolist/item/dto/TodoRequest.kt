package com.widehouse.todolist.item.dto

import com.widehouse.todolist.item.Todo
import com.widehouse.todolist.item.TodoStatus

data class TodoRequest(
    val title: String,
    val status: TodoStatus = TodoStatus.TODO
) {
    fun toEntity(): Todo {
        return Todo(
            title = title,
            status = status
        )
    }
}
