package org.ilya40umov.kc.reactive.demo

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class DemoApiClient {

    private val webClient = WebClient.create("http://127.0.0.1:8080")

    fun getRandomUserId(): Mono<Int> =
        webClient.get()
            .uri("/demo/random_user_id")
            .exchange()
            .flatMap { it.toEntity<String>() }
            .map { it.body?.replace("""\s+""".toRegex(), "")?.toInt() }

    fun getUserName(userId: Int): Mono<String> =
        webClient.get()
            .uri("/demo/user_name?id=$userId")
            .exchange()
            .flatMap { it.toEntity<String>() }
            .map { it.body }

    fun getRandomUserIds(limit: Int): Flux<Int> =
        webClient.get()
            .uri("/demo/random_user_ids?limit=$limit")
            .exchange()
            .flatMapMany { it.bodyToFlux<String>() }
            .map { it.replace("""\s+""".toRegex(), "").toInt() }
}