package com.widehouse.todolist.item

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor

interface TodoRepository : ReactiveMongoRepository<Todo, Long>, ReactiveQuerydslPredicateExecutor<Todo>
