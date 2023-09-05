package com.widehouse.todolist.item

import com.widehouse.todolist.config.MongoConfig
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@DataMongoTest
@Import(MongoConfig::class)
class ItemRepositoryTest(
    private val mongoTemplate: ReactiveMongoTemplate,
    private val repository: ItemRepository
) : StringSpec() {
    init {
        "save data then date property" {
            // given
            val item = Item("title", ItemStatus.DONE)
            // when
            val actual = repository.save(item).block()
            // then
            actual?.id.shouldNotBeNull()
            actual?.createdAt.shouldNotBeNull()
            actual?.updatedAt.shouldNotBeNull()
        }
    }
}
