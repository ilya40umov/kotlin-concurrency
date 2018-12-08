package org.ilya40umov.kc.reactive

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.toEntity
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class CombineResultsHandler {

    private val webClient = WebClient.create("http://127.0.0.1:8080")

    private fun getRandomUserId(): Mono<Int> =
        webClient.get()
            .uri("/demo/random_user_id")
            .exchange()
            .flatMap { it.toEntity<String>() }
            .map { it.body?.replace("""\s+""".toRegex(), "")?.toInt() }

    private fun getUserName(userId: Int): Mono<String> =
        webClient.get()
            .uri("/demo/user_name?id=$userId")
            .exchange()
            .flatMap { it.toEntity<String>() }
            .map { it.body }

    fun combineSingle(request: ServerRequest): Mono<ServerResponse> {
        return getRandomUserId().flatMap { userId ->
            getUserName(userId).map { userName -> userId to userName }
        }.flatMap { (userId, userName) ->
            ok().body(fromObject("#$userId: $userName"))
        }
    }

    private fun getRandomUserIds(): Flux<Int> =
        webClient.get()
            .uri("/demo/random_user_ids")
            .exchange()
            .flatMapMany { it.bodyToFlux<String>() }
            .map { it.replace("""\s+""".toRegex(), "").toInt() }

    fun combineStream(request: ServerRequest): Mono<ServerResponse> {
        val combinedStream = getRandomUserIds().flatMap { userId ->
            getUserName(userId)
                .map { userName -> "#$userId: $userName" }
        }.onBackpressureBuffer()
        return ok().bodyToServerSentEvents(combinedStream)
    }
}