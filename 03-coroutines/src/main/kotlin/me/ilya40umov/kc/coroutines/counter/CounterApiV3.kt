package me.ilya40umov.kc.coroutines.counter

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor

private sealed class IncrementorMsg
private class IncValue(val newValue: CompletableDeferred<Int>) : IncrementorMsg()
private class GetValue(val currentValue: CompletableDeferred<Int>) : IncrementorMsg()

@ObsoleteCoroutinesApi
fun Application.counterV3() {
    // or you can even get an actor!
    val incrementor = actor<IncrementorMsg> {
        var counter = 0
        for (msg in channel) {
            when (msg) {
                is IncValue -> msg.newValue.complete(++counter)
                is GetValue -> msg.currentValue.complete(counter)
            }
        }
    }
    routing {
        route("/counter") {
            get("/v3") {
                val valueHolder = CompletableDeferred<Int>()
                incrementor.send(GetValue(valueHolder))
                call.respondText("${valueHolder.await()}\n")
            }
            post("/v3") {
                val valueHolder = CompletableDeferred<Int>()
                incrementor.send(IncValue(valueHolder))
                call.respondText("${valueHolder.await()}\n")
            }
        }
    }
}