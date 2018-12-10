package org.ilya40umov.kc.coroutines.stream

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.client.request.get
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.reactive.openSubscription

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
                    .response<String> { _, content -> content.asString() }
                    .map { event -> event.replace("[^0-9]+".toRegex(), "") }
                    .filter { rawUserId -> !rawUserId.isNullOrBlank() }
                    .map { rawUserId -> rawUserId.toInt() }
                    .openSubscription()
            val winners = ids.map { userId ->
                val userName = client.get<String>("http://127.0.0.1:9090/demo/user_name?id=$userId")
                "Winner is: #$userId - $userName"
            }
            call.respondSse(winners)
        }
    }
}