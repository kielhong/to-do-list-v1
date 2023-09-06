package com.widehouse.todolistt.item

import com.widehouse.todolist.item.Todo
import com.widehouse.todolist.item.TodoStatus
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

object TodoFixtures {
    val todo = Todo("title", TodoStatus.TODO, LocalDateTime.now().plusDays(1)).apply {
        ReflectionTestUtils.setField(this, "id", "1")
    }
    val doing = Todo("title doing", TodoStatus.DOING, LocalDateTime.now().plusDays(1)).apply {
        ReflectionTestUtils.setField(this, "id", "1")
    }
    val done = Todo("title done", TodoStatus.DONE, LocalDateTime.now().plusDays(1)).apply {
        ReflectionTestUtils.setField(this, "id", "1")
    }
}
