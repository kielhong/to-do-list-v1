package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.ItemRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ItemService(
    private val itemRepository: ItemRepository
) {
    fun createItem(request: ItemRequest): Mono<Item> {
        return itemRepository.save(request.toEntity())
    }

    fun updateItem(id: Long, request: ItemRequest): Mono<Item> {
        return Mono.empty()
    }
}
