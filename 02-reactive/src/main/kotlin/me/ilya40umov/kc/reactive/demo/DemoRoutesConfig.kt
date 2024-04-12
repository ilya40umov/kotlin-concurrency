package me.ilya40umov.kc.reactive.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import org.springframework.web.reactive.function.server.sse
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
                ok().body(BodyInserters.fromValue("$randomId"))
            }
            GET("/user_name") { request ->
                val userId = request
                    .queryParam("id")
                    .map {
                        it.toInt()
                    }.orElse(null)
                when (userId) {
                    null -> badRequest().body(
                        BodyInserters.fromValue(
                            "Param `id` is missing!\n"
                        )
                    )
                    else -> {
                        val userName = if (userId % 2 == 0) "Jack" else "Jill"
                        ok().body(BodyInserters.fromValue(userName))
                    }
                }
            }
            GET("/random_user_ids") { request ->
                val limit =
                    request.queryParam("limit")
                        .map { it.toInt() }
                        .orElseGet { 10 }
                val ids =
                    Flux.generate<Int> {
                        it.next(Random.nextInt(1, Int.MAX_VALUE))
                    }.delayElements(
                        Duration.ofMillis(300L)
                    ).map {
                        it.toString()
                    }.take(limit.toLong())
                ok().sse().body(ids)
            }
        }
    }
}