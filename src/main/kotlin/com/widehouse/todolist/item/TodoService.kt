package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.TodoRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    fun createItem(request: TodoRequest): Mono<Todo> {
        return todoRepository.save(request.toEntity())
    }

    fun updateItem(id: Long, request: TodoRequest): Mono<Todo> {
        return todoRepository.findById(id)
            .map {
                it.apply {
                    update(request.title, request.status, request.dueDate)
                }
            }
            .flatMap {
                todoRepository.save(it)
            }
    }
}
