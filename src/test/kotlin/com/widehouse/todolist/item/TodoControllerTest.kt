package com.widehouse.todolist.item

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.todolist.item.dto.TodoRequest
import com.widehouse.todolistt.item.TodoFixtures
import io.kotest.core.spec.style.FreeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@WebFluxTest(TodoController::class)
class TodoControllerTest(
    private val webClient: WebTestClient,
    @MockkBean
    private val todoService: TodoService
) : FreeSpec() {
    override fun extensions() = listOf(SpringExtension)

    init {
        "item create" {
            // given
            val createdItem = TodoFixtures.todo
            every { todoService.createItem(any()) } returns Mono.just(createdItem)
            // when
            val request = TodoRequest("title todo", TodoStatus.TODO, null)
            val response = webClient
                .post()
                .uri("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
            // then
            response.expectStatus().isOk
            response.expectBody().jsonPath("$.id").isEqualTo(createdItem.id)
            verify {
                todoService.createItem(
                    withArg {
                        it.title shouldBe request.title
                        it.status shouldBe request.status
                        it.dueDate shouldBe request.dueDate
                    }
                )
            }
        }

        "item update" {
            // given
            val id = 2L
            val updatedItem = TodoFixtures.doing
            every { todoService.updateItem(any(), any()) } returns Mono.just(updatedItem)
            // when
            val request = TodoRequest("title doing", TodoStatus.DOING, LocalDateTime.now().plusDays(5))
            val response = webClient
                .put()
                .uri("/items/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
            // then
            response.expectStatus().isOk
            response.expectBody().jsonPath("$.id").isEqualTo(updatedItem.id)
            verify {
                todoService.updateItem(
                    id,
                    withArg {
                        it.title shouldBe request.title
                        it.status shouldBe request.status
                        it.dueDate shouldBe request.dueDate
                    }
                )
            }
        }
    }
}
