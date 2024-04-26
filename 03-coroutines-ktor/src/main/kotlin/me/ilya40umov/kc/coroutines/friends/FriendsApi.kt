@file:Suppress("DuplicatedCode")

package me.ilya40umov.kc.coroutines.friends

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

fun Application.friends(
    fakeService: FakeService = FakeService()
) {
    routing {
        get("/db_calls") {
            call.respondText("Db calls count: ${fakeService.getDbCallsCount()}\n")
        }

        get("/users/{userId}") {
            val user = call.parameters["userId"]?.let {
                fakeService.getUserById(it.toLong())
            }
            call.respondText("Name: ${user?.name}\n")
        }

        get("/users/{userId}/friends/v1") {
            val user = call.parameters["userId"]?.let {
                fakeService.getUserById(it.toLong())
            } ?: return@get
            val friends = user.friendIds.map { friendId ->
                fakeService.getUserById(friendId)
            }
            call.respondText(friends.joinToString(separator = "") { "Friend: ${it.name}\n" })
        }
        get("/users/{userId}/friends/v2") {
            val user = call.parameters["userId"]?.let {
                fakeService.getUserById(it.toLong())
            } ?: return@get
            val friends = user.friendIds.map { friendId ->
                async { fakeService.getUserById(friendId) }
            }.awaitAll()
            call.respondText(friends.joinToString(separator = "") { "Friend: ${it.name}\n" })
        }

        get("/users/{userId}/failing_friends/v1") {
            val user = call.parameters["userId"]?.let {
                fakeService.getUserById(it.toLong())
            } ?: return@get
            val friends = (listOf(999L) + user.friendIds).map { friendId ->
                async { fakeService.getUserById(friendId) }
            }.awaitAll()
            call.respondText(friends.joinToString(separator = "") { "Friend: ${it.name}\n" })
        }
        get("/users/{userId}/failing_friends/v2") {
            try {
                val user = call.parameters["userId"]?.let {
                    fakeService.getUserById(it.toLong())
                } ?: return@get
                val friends = (listOf(999L) + user.friendIds).map { friendId ->
                    async { fakeService.getUserById(friendId) }
                }.awaitAll()
                call.respondText(friends.joinToString(separator = "") { "Friend: ${it.name}\n" })
            } catch (e: Exception) {
                delay(10L)
                call.respondText { "Failed: ${e.message}" }
            }
        }
        get("/users/{userId}/failing_friends/v3") {
            try {
                val user = call.parameters["userId"]?.let {
                    fakeService.getUserById(it.toLong())
                } ?: return@get
                // coroutineScope: When any child coroutine in this scope fails,
                // this scope fails and all the rest of the children are cancelled
                val friends = coroutineScope {
                    (listOf(999L) + user.friendIds).map { friendId ->
                        async { fakeService.getUserById(friendId) }
                    }.awaitAll()
                }
                call.respondText(friends.joinToString(separator = "") { "Friend: ${it.name}\n" })
            } catch (e: Exception) {
                // e.printStackTrace()
                delay(10L)
                call.respondText { "Failed: ${e.message}" }
            }
        }
    }
}