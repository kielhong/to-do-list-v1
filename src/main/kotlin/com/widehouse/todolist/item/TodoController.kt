package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.CommandResponse
import com.widehouse.todolist.item.dto.TodoRequest
import com.widehouse.todolist.item.dto.TodoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("todos")
class TodoController(
    private val todoService: TodoService
) {
    @GetMapping
    fun listTodos(): Flux<TodoResponse> {
        return todoService.listTodos()
            .map { TodoResponse.from(it) }
    }

    @GetMapping(params = ["status"])
    fun listTodos(status: TodoStatus): Flux<TodoResponse> {
        return todoService.listTodos(status)
            .map { TodoResponse.from(it) }
    }

    @PostMapping
    fun createItem(
        @RequestBody request: TodoRequest
    ): Mono<CommandResponse> {
        return todoService.createItem(request)
            .map { CommandResponse(it.id) }
    }

    @PutMapping("{id}")
    fun changeItemStatus(
        @PathVariable id: Long,
        @RequestBody request: TodoRequest
    ): Mono<CommandResponse> {
        return todoService.updateItem(id, request)
            .map { CommandResponse(it.id) }
    }
}
