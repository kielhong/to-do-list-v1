package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.ItemRequest
import com.widehouse.todolistt.item.ItemFixtures
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class ItemServiceTest : StringSpec() {
    private val itemRepository: ItemRepository = mockk()
    private val service = ItemService(itemRepository)

    init {
        "create Item" {
            // given
            val item = ItemFixtures.todo
            every { itemRepository.save(any()) } returns Mono.just(item)
            // when
            val request = ItemRequest("title")
            val actual = service.createItem(request)
            // then
            StepVerifier.create(actual)
                .assertNext {
                    it.id shouldBe item.id
                    it.title shouldBe item.title
                    it.status shouldBe item.status
                }
                .verifyComplete()
        }
    }
}
