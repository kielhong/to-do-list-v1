package com.widehouse.todolist.item

import com.querydsl.core.types.Predicate
import com.widehouse.todolist.item.dto.TodoRequest
import com.widehouse.todolistt.item.TodoFixtures
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

class TodoServiceTest : FreeSpec() {
    private val todoRepository: TodoRepository = mockk()
    private val service = TodoService(todoRepository)

    init {
        "create Todo" {
            // given
            val item = TodoFixtures.todo
            every { todoRepository.save(any()) } returns Mono.just(item)
            // when
            val request = TodoRequest("title", TodoStatus.TODO, LocalDateTime.now().plusDays(1))
            val actual = service.createItem(request)
            // then
            StepVerifier.create(actual)
                .assertNext { it shouldBe item }
                .verifyComplete()
        }

        "update Todo" {
            // given
            val id = 1L
            val request = TodoRequest("title doing", TodoStatus.DOING, null)
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
                        it.dueDate shouldBe request.dueDate
                    }
                )
            }
        }

        "list Todos" - {
            "list all Toods" {
                // given
                val todos = listOf(
                    Pair(TodoStatus.DONE, LocalDateTime.now()),
                    Pair(TodoStatus.DOING, LocalDateTime.now()),
                    Pair(TodoStatus.TODO, LocalDateTime.now()),
                    Pair(TodoStatus.TODO, null)
                )
                    .mapIndexed { index, pair ->
                        TodoFixtures.todo(index.toString(), "title $index", pair.first, pair.second)
                    }
                every { todoRepository.findAll() } returns Flux.fromIterable(todos)
                // when
                val actual = service.listTodos()
                // then
                StepVerifier.create(actual)
                    .assertNext {
                        it.status shouldBe TodoStatus.TODO
                        it.dueDate shouldBe null
                    }
                    .assertNext {
                        it.status shouldBe TodoStatus.TODO
                        it.dueDate shouldNotBe null
                    }
                    .assertNext {
                        it.status shouldBe TodoStatus.DOING
                    }
                    .assertNext {
                        it.status shouldBe TodoStatus.DONE
                    }
                    .verifyComplete()
            }

            "list by status" {
                val todos = listOf(
                    Pair(TodoStatus.TODO, LocalDateTime.now()),
                    Pair(TodoStatus.TODO, null)
                )
                    .mapIndexed { index, pair ->
                        TodoFixtures.todo(index.toString(), "title $index", pair.first, pair.second)
                    }
                every { todoRepository.findAll(ofType(Predicate::class)) } returns Flux.fromIterable(todos)
                // when
                val actual = service.listTodos(TodoStatus.TODO)
                // then
                StepVerifier.create(actual)
                    .assertNext {
                        it.status shouldBe TodoStatus.TODO
                        it.dueDate shouldBe null
                    }
                    .assertNext {
                        it.status shouldBe TodoStatus.TODO
                        it.dueDate shouldNotBe null
                    }
                    .verifyComplete()
            }
        }
    }
}
