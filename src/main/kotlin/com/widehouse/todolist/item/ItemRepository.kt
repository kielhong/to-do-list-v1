package com.widehouse.todolist.item

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ItemRepository : ReactiveMongoRepository<Item, Long>
