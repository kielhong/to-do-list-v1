package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.CreateResponse
import com.widehouse.todolist.item.dto.ItemRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("items")
class ItemController(
    private val itemService: ItemService
) {
    @PostMapping
    fun createItem(
        @RequestBody request: ItemRequest
    ): CreateResponse {
        return itemService.createItem(request).run {
            CreateResponse(this.id)
        }
    }
}
