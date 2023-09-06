package com.widehouse.todolist.item

import com.widehouse.todolist.config.MongoConfig
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import java.time.LocalDateTime

@DataMongoTest
@Import(MongoConfig::class)
class TodoRepositoryTest(
    private val mongoTemplate: ReactiveMongoTemplate,
    private val repository: TodoRepository
) : StringSpec() {
    override fun extensions() = listOf(SpringExtension)

    init {
        "save data then date property" {
            // given
            val todo = Todo("title", TodoStatus.DONE, LocalDateTime.now())
            // when
            val actual = repository.save(todo).block()
            // then
            actual?.id.shouldNotBeNull()
            actual?.createdAt.shouldNotBeNull()
            actual?.updatedAt.shouldNotBeNull()
        }
    }
}
