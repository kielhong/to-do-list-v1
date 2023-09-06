package com.widehouse.todolist.item

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.todolistt.item.TodoFixtures
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(TodoController::class)
class TodoControllerTest(
    private val webClient: WebTestClient,
    @MockkBean
    private val todoService: TodoService
) : FreeSpec() {
    init {
        "item 1개 생성" {
            // given
            val createdItem = TodoFixtures.todo
            every { todoService.createItem(any()) } returns Mono.just(createdItem)
            // when
            val request = mapOf("title" to "title")
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
                        it.title shouldBe "title"
                        it.status shouldBe TodoStatus.TODO
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
            val request = mapOf("title" to "title doing", "status" to TodoStatus.DOING)
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
                        it.title shouldBe "title doing"
                        it.status shouldBe TodoStatus.DOING
                    }
                )
            }
        }
    }
}
