package com.widehouse.todolist.item

import com.widehouse.todolist.config.MongoConfig
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@DataMongoTest
@Import(MongoConfig::class)
class TodoRepositoryTest(
    private val mongoTemplate: ReactiveMongoTemplate,
    private val repository: TodoRepository
) : StringSpec() {
    init {
        "save data then date property" {
            // given
            val todo = Todo("title", TodoStatus.DONE)
            // when
            val actual = repository.save(todo).block()
            // then
            actual?.id.shouldNotBeNull()
            actual?.createdAt.shouldNotBeNull()
            actual?.updatedAt.shouldNotBeNull()
        }
    }
}
