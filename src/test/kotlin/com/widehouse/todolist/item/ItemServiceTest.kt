package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.ItemRequest
import com.widehouse.todolistt.item.ItemFixtures
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.util.ReflectionTestUtils.setField
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.UUID

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

        "update Item" {
            // given
            val id = 1L
            val request = ItemRequest("title doing", ItemStatus.DOING)
            val updatedItem = ItemFixtures.doing
            every { itemRepository.findById(id) } returns Mono.just(ItemFixtures.todo)
            every { itemRepository.save(any()) } returns Mono.just(updatedItem)
            // when
            val actual = service.updateItem(id, request)
            // then
            StepVerifier.create(actual)
                .assertNext { it shouldBe updatedItem }
                .verifyComplete()
            verify {
                itemRepository.save(
                    withArg {
                        it.title shouldBe request.title
                        it.status shouldBe request.status
                    }
                )
            }
        }
    }
}
