package me.ilya40umov.kc.reactive.webflux

import me.ilya40umov.kc.reactive.demo.DemoApiClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.sse
import reactor.core.publisher.Mono

@Component
class WebfluxWinnerHandler(
    private val demoApiClient: DemoApiClient
) {

    fun determineSingle(request: ServerRequest): Mono<ServerResponse> {
        return demoApiClient.getRandomUserId().flatMap { userId ->
            demoApiClient.getUserName(userId).map { userName ->
                userId to userName
            }
        }.flatMap { (userId, userName) ->
            ok().body(fromValue("Winner is: #$userId - $userName\n"))
        }
    }

    fun determineStream(request: ServerRequest): Mono<ServerResponse> {
        val limit =
            request.queryParam("limit")
                .map { it.toInt() }
                .orElseGet { 10 }
        val combinedStream =
            demoApiClient.getRandomUserIds(limit).flatMap { userId ->
                demoApiClient.getUserName(userId)
                    .map { userName ->
                        "Winner is: #$userId - $userName"
                    }
            }.onBackpressureBuffer()
        return ok().sse().body(combinedStream)
    }
}