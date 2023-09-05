package com.widehouse.todolistt.item

import com.widehouse.todolist.item.Item
import com.widehouse.todolist.item.ItemStatus
import org.springframework.test.util.ReflectionTestUtils

object ItemFixtures {
    val todo = Item("title", ItemStatus.TODO).apply {
        ReflectionTestUtils.setField(this, "id", "1")
    }
    val doing = Item("title doing", ItemStatus.DOING).apply {
        ReflectionTestUtils.setField(this, "id", "1")
    }
}
