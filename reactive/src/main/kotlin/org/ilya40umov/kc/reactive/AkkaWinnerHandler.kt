package org.ilya40umov.kc.reactive

import akka.actor.ActorSystem
import org.ilya40umov.kc.reactive.actors.SingleWinnerChooser
import org.ilya40umov.kc.reactive.actors.SingleWinnerChooser.Companion.ChooseWinner
import org.ilya40umov.kc.reactive.actors.SingleWinnerChooser.Companion.WinnerDetails
import org.ilya40umov.kc.reactive.akka.actorOf
import org.ilya40umov.kc.reactive.akka.ask
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class AkkaWinnerHandler(
    private val actorSystem: ActorSystem
) {
    fun determineSingle(request: ServerRequest): Mono<ServerResponse> {
        val chooser = actorSystem.actorOf(SingleWinnerChooser::class, "single-winner-chooser-${UUID.randomUUID()}")
        val winnerDetails = chooser.ask<WinnerDetails>(ChooseWinner)
        return Mono.fromCompletionStage<WinnerDetails>(winnerDetails).flatMap {
            ok().body(fromObject("Winner is: ${it.details}\n"))
        }
    }
}