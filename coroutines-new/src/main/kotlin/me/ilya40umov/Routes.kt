package me.ilya40umov

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

@Suppress("unused") // Referenced in application.conf
fun Application.routes(
    fakeService: FakeService = FakeService()
) {
    routing {

        get("/") {
            call.respondText("Db calls count: ${fakeService.getDbCallsCount()}\n")
        }

        get("/{userId}/") {
            val user = call.parameters["userId"]?.let {
                fakeService.getUserById(it.toLong())
            }
            call.respondText("Name: ${user?.name}\n")
        }

        get("/{userId}/friends/v1") {
            val user = call.parameters["userId"]?.let {
                fakeService.getUserById(it.toLong())
            } ?: return@get
            val friends = user.friendIds.map { friendId ->
                fakeService.getUserById(friendId)
            }
            call.respondText(friends.joinToString(separator = "") { "Friend: ${it.name}\n" })
        }
        get("/{userId}/friends/v2") {
            val user = call.parameters["userId"]?.let {
                fakeService.getUserById(it.toLong())
            } ?: return@get
            val friends = user.friendIds.map { friendId ->
                async { fakeService.getUserById(friendId) }
            }.awaitAll()
            friends.forEach { call.respondText("Friend: ${it.name}\n") }
        }

        get("/{userId}/failing_friends/v1/") {
            val user = call.parameters["userId"]?.let {
                fakeService.getUserById(it.toLong())
            } ?: return@get
            val friends = (listOf(999L) + user.friendIds).map { friendId ->
                async { fakeService.getUserById(friendId) }
            }.awaitAll()
            friends.forEach { call.respondText("Friend: ${it.name}\n") }
        }
        get("/{userId}/failing_friends/v2/") {
            try {
                val user = call.parameters["userId"]?.let {
                    fakeService.getUserById(it.toLong())
                } ?: return@get
                val friends = coroutineScope {
                    (listOf(999L) + user.friendIds).map { friendId ->
                        async { fakeService.getUserById(friendId) }
                    }.awaitAll()
                }
                friends.forEach { call.respondText("Friend: ${it.name}\n") }
            } catch (e: Exception) {
                call.respondText { "Failed: ${e.message}" }
            }
        }
    }
}