package org.ilya40umov.kc.coroutines.single

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.winnerSingle() {
    val client = HttpClient()
    routing {
        get("/winner/single") {
            val userId = client.get<String>(
                "http://127.0.0.1:9090/demo/random_user_id"
            ).let { userId ->
                userId.toInt()
            }
            val userName = client.get<String>(
                "http://127.0.0.1:9090/demo/user_name?id=$userId"
            )
            call.respondText { "Winner is: #$userId - $userName\n" }
        }
    }
}