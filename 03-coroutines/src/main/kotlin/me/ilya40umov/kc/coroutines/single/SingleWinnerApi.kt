package me.ilya40umov.kc.coroutines.single

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.winnerSingle() {
    val client = HttpClient()
    routing {
        get("/winner/single") {
            val userId =
                client
                    .get("http://127.0.0.1:9090/demo/random_user_id")
                    .bodyAsText(Charsets.UTF_8)
                    .toInt()
            val userName =
                client
                    .get("http://127.0.0.1:9090/demo/user_name?id=$userId")
                    .bodyAsText(Charsets.UTF_8)
            call.respondText { "Winner is: #$userId - $userName\n" }
        }
    }
}