package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.CreateResponse
import com.widehouse.todolist.item.dto.ItemRequest
import org.springframework.web.bind.annotation.PostMapping
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
    ): Mono<CreateResponse> {
        return itemService.createItem(request)
            .map { CreateResponse(it.id) }
    }
}
