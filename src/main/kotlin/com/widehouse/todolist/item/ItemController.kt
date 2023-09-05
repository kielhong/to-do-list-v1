package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.CommandResponse
import com.widehouse.todolist.item.dto.ItemRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("items")
class ItemController(
    private val itemService: ItemService
) {
    @PostMapping
    fun createItem(
        @RequestBody request: ItemRequest
    ): Mono<CommandResponse> {
        return itemService.createItem(request)
            .map { CommandResponse(it.id) }
    }

    @PutMapping("{id}")
    fun changeItemStatus(
        @PathVariable id: Long,
        @RequestBody request: ItemRequest
    ): Mono<CommandResponse> {
        return itemService.updateItem(id, request)
            .map { CommandResponse(it.id) }
    }
}
