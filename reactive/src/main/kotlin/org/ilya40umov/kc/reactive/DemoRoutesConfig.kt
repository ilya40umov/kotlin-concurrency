package org.ilya40umov.kc.reactive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import java.time.Duration
import kotlin.random.Random

@Configuration
class DemoRoutesConfig {

    @Bean
    fun demoRoutes() = router {
        path("/demo").nest {
            GET("/random_user_id") {
                val randomId = Random.nextInt(1, Int.MAX_VALUE)
                ok().body(BodyInserters.fromObject("$randomId"))
            }
            GET("/user_name") { request ->
                val userId = request.queryParam("id").map { it.toInt() }.orElse(null)
                when (userId) {
                    null -> badRequest().body(BodyInserters.fromObject("Param `id` is missing!\n"))
                    else -> {
                        val userName = if (userId % 2 == 0) "Jack" else "Jill"
                        ok().body(BodyInserters.fromObject(userName))
                    }
                }
            }
            GET("/random_user_ids") { request ->
                val limit =
                    request.queryParam("limit")
                        .map { it.toInt() }
                        .orElseGet { 10 }
                val ids =
                    Flux.generate<Int> { it.next(Random.nextInt(1, Int.MAX_VALUE)) }
                        .delayElements(Duration.ofMillis(300L))
                        .map { it.toString() }
                        .take(limit.toLong())
                ok().bodyToServerSentEvents(ids)
            }
        }
    }
}