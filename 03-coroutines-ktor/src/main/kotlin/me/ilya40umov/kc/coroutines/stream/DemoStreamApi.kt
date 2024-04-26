package me.ilya40umov.kc.coroutines.stream

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

@ExperimentalCoroutinesApi
fun Application.demoStream() {
    routing {
        get("/demo/random_user_ids") {
            val limit =
                context.request.queryParameters["limit"]?.toInt() ?: 10
            val flow = flow {
                repeat(limit) {
                    emit(Random.nextInt(1, Int.MAX_VALUE))
                    delay(200)
                }
            }
            call.respondSse(flow)
        }
    }
}