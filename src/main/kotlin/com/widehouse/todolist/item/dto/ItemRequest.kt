package com.widehouse.todolist.item.dto

import com.widehouse.todolist.item.Item
import com.widehouse.todolist.item.ItemStatus

data class ItemRequest(
    val title: String,
    val status: ItemStatus = ItemStatus.TODO
) {
    fun toEntity(): Item {
        return Item(
            id = 0L,
            title = title,
            status = status
        )
    }
}
