package me.ilya40umov.kc.coroutines.counter

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.newSingleThreadContext

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun Application.counterV2() {
    // we could use locks or atomic here, but...
    val incrementor = object {
        // let's use thread-confinement for a change
        val context = newSingleThreadContext("counter-owner")
        @Volatile // volatile to make sure it's visible in 'get' operation
        var counter = 0
    }
    routing {
        route("/counter") {
            get("/v2") {
                call.respondText("${incrementor.counter}\n")
            }
            post("/v2") {
                val counterValue = async(incrementor.context) {
                    ++incrementor.counter
                }
                call.respondText { "${counterValue.await()}\n" }
            }
        }
    }
}