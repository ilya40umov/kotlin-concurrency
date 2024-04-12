package me.ilya40umov.kc.coroutines.stream

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun Application.winnerStream() {
    val client = io.ktor.client.HttpClient()
    val nettyClient = reactor.netty.http.client.HttpClient.create()
    routing {
        get("/winner/stream") {
            val limit = context.request.queryParameters["limit"]?.toInt() ?: 10
            val ids =
                nettyClient
                    .get()
                    .uri("http://127.0.0.1:9090/demo/random_user_ids?limit=$limit")
                    .response { _, content -> content.asString() }
                    .map { event -> event.replace("[^0-9]+".toRegex(), "") }
                    .filter { rawUserId -> !rawUserId.isNullOrBlank() }
                    .map { rawUserId -> rawUserId.toInt() }
                    .asFlow()
            val winners = ids.map { userId ->
                val userName =
                    client
                        .get("http://127.0.0.1:9090/demo/user_name?id=$userId")
                        .bodyAsText(Charsets.UTF_8)
                "Winner is: #$userId - $userName"
            }
            call.respondSse(winners)
        }
    }
}