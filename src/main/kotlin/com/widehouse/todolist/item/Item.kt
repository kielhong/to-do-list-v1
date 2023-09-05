package com.widehouse.todolist.item

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "item")
class Item(
    @Id
    val id: Long,
    var title: String,
    var status: ItemStatus
) {
    fun update(title: String, status: ItemStatus) {
        this.title = title
        this.status = status
    }
}
