package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.TodoRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Comparator.comparing

@Service
@Transactional
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

    fun listTodos(): Flux<Todo> {
        return todoRepository.findAll()
            .sort(
                comparing(Todo::status, comparing(TodoStatus::order))
                    .thenComparing(Todo::dueDate, nullsFirst())
            )
    }

    fun listTodos(status: TodoStatus): Flux<Todo> {
        val predict = QTodo.todo.status.eq(status)

        return todoRepository.findAll(predict)
            .sort(
                comparing(Todo::status, comparing(TodoStatus::order))
                    .thenComparing(Todo::dueDate, nullsFirst())
            )
    }
}
