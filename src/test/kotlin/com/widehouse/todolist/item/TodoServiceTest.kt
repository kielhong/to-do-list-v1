package com.widehouse.todolist.item

import com.widehouse.todolist.item.dto.TodoRequest
import com.widehouse.todolistt.item.TodoFixtures
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class TodoServiceTest : StringSpec() {
    private val todoRepository: TodoRepository = mockk()
    private val service = TodoService(todoRepository)

    init {
        "create Item" {
            // given
            val item = TodoFixtures.todo
            every { todoRepository.save(any()) } returns Mono.just(item)
            // when
            val request = TodoRequest("title")
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
            val request = TodoRequest("title doing", TodoStatus.DOING)
            val updatedItem = TodoFixtures.doing
            every { todoRepository.findById(id) } returns Mono.just(TodoFixtures.todo)
            every { todoRepository.save(any()) } returns Mono.just(updatedItem)
            // when
            val actual = service.updateItem(id, request)
            // then
            StepVerifier.create(actual)
                .assertNext { it shouldBe updatedItem }
                .verifyComplete()
            verify {
                todoRepository.save(
                    withArg {
                        it.title shouldBe request.title
                        it.status shouldBe request.status
                    }
                )
            }
        }
    }
}
