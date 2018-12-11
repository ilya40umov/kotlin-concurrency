package org.ilya40umov.kc.coroutines.stream

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlin.random.Random

@ExperimentalCoroutinesApi
fun Application.demoStream() {
    routing {
        get("/demo/random_user_ids") {
            val limit =
                context.request.queryParameters["limit"]?.toInt() ?: 10
            val channel = produce {
                repeat(limit) {
                    send(Random.nextInt(1, Int.MAX_VALUE))
                    delay(200)
                }
            }
            call.respondSse(channel)
        }
    }
}