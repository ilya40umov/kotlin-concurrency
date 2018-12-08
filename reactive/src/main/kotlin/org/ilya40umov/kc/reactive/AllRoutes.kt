package org.ilya40umov.kc.reactive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import java.time.Duration
import kotlin.random.Random

@Configuration
class AllRoutes(
    private val combineResultsHandler: CombineResultsHandler
) {

    @Bean
    fun demoRoutes() = router {
        path("/demo").nest {
            GET("/random_user_id") {
                val randomId = Random.nextInt(1, Int.MAX_VALUE)
                ok().body(fromObject("$randomId\n"))
            }
            GET("/user_name") { request ->
                request
                    .queryParam("id")
                    .map { it.toInt() }
                    .map { id -> if (id % 2 == 0) "Jack" else "Jill" }
                    .map { userName ->
                        ok().body(fromObject("$userName\n"))
                    }.orElseGet {
                        badRequest().body(fromObject("Param `id` is missing!\n"))
                    }
            }
            GET("/random_user_ids") { request ->
                val limit = request.queryParam("limit").map { it.toLong() }.orElseGet { 10L }
                val ids = Flux.generate<Int> { sink ->
                    sink.next(Random.nextInt(1, Int.MAX_VALUE))
                }.delayElements(Duration.ofMillis(300L)).map { it.toString() }.take(limit)
                ok().bodyToServerSentEvents(ids)
            }
        }
    }

    @Bean
    fun mainRoutes() = router {
        path("/combine").nest {
            GET("/single", combineResultsHandler::combineSingle)
            GET("/stream", combineResultsHandler::combineStream)
        }
    }
}