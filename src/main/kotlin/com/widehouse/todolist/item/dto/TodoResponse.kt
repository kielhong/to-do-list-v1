package com.widehouse.todolist.item.dto

import com.widehouse.todolist.item.Todo
import com.widehouse.todolist.item.TodoStatus
import java.time.LocalDateTime

data class TodoResponse(
    val title: String,
    val status: TodoStatus = TodoStatus.TODO,
    val dueDate: LocalDateTime?
) {
    companion object {
        fun from(todo: Todo): TodoResponse =
            TodoResponse(
                todo.title,
                todo.status,
                todo.dueDate
            )
    }
}
