package com.widehouse.todolistt.item

import com.widehouse.todolist.item.Item
import com.widehouse.todolist.item.ItemStatus

object ItemFixtures {
    val todo = Item(1L, "title", ItemStatus.TODO)
    fun doing(id: Long = 2L) = Item(id, "title doing", ItemStatus.DOING)
}
