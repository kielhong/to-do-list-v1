package com.widehouse.todolist.item

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "item")
class Item(
    @Id
    val id: Long,
    val title: String,
    val status: ItemStatus
)
