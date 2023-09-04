package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.ItemRequest
import org.springframework.stereotype.Service

@Service
class ItemService {
    fun createItem(request: ItemRequest): Item {
        return Item(0L, "", ItemStatus.TODO)
    }
}
