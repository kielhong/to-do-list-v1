package com.widehouse.todolistt.item

import com.widehouse.todolist.item.Todo
import com.widehouse.todolist.item.TodoStatus
import org.springframework.test.util.ReflectionTestUtils

object TodoFixtures {
    val todo = Todo("title", TodoStatus.TODO).apply {
        ReflectionTestUtils.setField(this, "id", "1")
    }
    val doing = Todo("title doing", TodoStatus.DOING).apply {
        ReflectionTestUtils.setField(this, "id", "1")
    }
}
