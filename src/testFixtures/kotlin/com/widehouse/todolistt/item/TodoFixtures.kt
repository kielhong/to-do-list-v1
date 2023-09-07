package com.widehouse.todolistt.item

import com.widehouse.todolist.item.Todo
import com.widehouse.todolist.item.TodoStatus
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

object TodoFixtures {
    fun todo(
        id: String,
        title: String,
        status: TodoStatus,
        dueDate: LocalDateTime? = LocalDateTime.now().plusDays(1)
    ) = Todo(title, status, dueDate).apply {
        ReflectionTestUtils.setField(this, "id", id)
    }

    val todo = todo("1", "title todo", TodoStatus.TODO)
    val doing = todo("1", "title doing", TodoStatus.DOING)
    val done = todo("1", "title done", TodoStatus.DONE)
}
