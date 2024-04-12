package me.ilya40umov.kc.reactive.akka

import akka.actor.ActorSystem
import me.ilya40umov.kc.reactive.akka.actors.SingleWinnerChooser
import me.ilya40umov.kc.reactive.akka.actors.SingleWinnerChooser.Companion.ChooseWinner
import me.ilya40umov.kc.reactive.akka.actors.SingleWinnerChooser.Companion.WinnerDetails
import me.ilya40umov.kc.reactive.akka.helper.actorOf
import me.ilya40umov.kc.reactive.akka.helper.ask
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.UUID

@Component
class AkkaWinnerHandler(
    private val actorSystem: ActorSystem
) {
    fun determineSingle(request: ServerRequest): Mono<ServerResponse> {
        val chooser = actorSystem.actorOf(
            SingleWinnerChooser::class,
            "single-winner-chooser-${UUID.randomUUID()}"
        )
        val winnerDetails = chooser.ask<WinnerDetails>(
            ChooseWinner,
            Duration.ofSeconds(10)
        )
        return Mono.fromCompletionStage(winnerDetails)
            .flatMap {
                ok().body(fromValue("Winner is: ${it.details}\n"))
            }.onErrorResume { error ->
                status(500).body(
                    fromValue("Failed to complete due to: ${error.javaClass.name} ${error.message}")
                )
            }
    }
}