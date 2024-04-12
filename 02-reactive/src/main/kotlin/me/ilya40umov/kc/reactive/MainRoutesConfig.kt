package me.ilya40umov.kc.reactive

import me.ilya40umov.kc.reactive.akka.AkkaWinnerHandler
import me.ilya40umov.kc.reactive.webflux.WebfluxWinnerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class MainRoutesConfig(
    private val webfluxWinnerHandler: WebfluxWinnerHandler,
    private val akkaWinnerHandler: AkkaWinnerHandler
) {
    @Bean
    fun mainRoutes() = router {
        path("/webflux/winner").nest {
            GET("/single", webfluxWinnerHandler::determineSingle)
            GET("/stream", webfluxWinnerHandler::determineStream)
        }
        path("/akka/winner").nest {
            GET("/single", akkaWinnerHandler::determineSingle)
        }
    }
}