package com.widehouse.todolist.item

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
class Todo(
    var title: String,
    var status: TodoStatus,
    var dueDate: LocalDateTime?
) {
    @Id
    lateinit var id: String

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    fun update(title: String, status: TodoStatus, dueDate: LocalDateTime?) {
        this.title = title
        this.status = status
        this.dueDate = dueDate
    }
}
