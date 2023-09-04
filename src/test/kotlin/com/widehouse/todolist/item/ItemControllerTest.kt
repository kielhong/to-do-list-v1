package com.widehouse.todolist.item

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.todolist.item.ItemStatus.TODO
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(ItemController::class)
class ItemControllerTest(
    private val webClient: WebTestClient,
    @MockkBean
    private val itemService: ItemService
) : StringSpec() {
    init {
        "item 1개 생성" {
            // given
            every { itemService.createItem(any()) } returns Item(id = 1L, title = "title", status = TODO)
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
            response.expectBody().jsonPath("$.id").isEqualTo(1L)
            verify {
                itemService.createItem(
                    withArg {
                        it.title shouldBe "title"
                    }
                )
            }
        }
    }
}
