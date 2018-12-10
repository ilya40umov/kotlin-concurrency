package org.ilya40umov.kc.coroutines.counter

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.newSingleThreadContext

@ObsoleteCoroutinesApi
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
                call.respondText { "$counterValue\n" }
            }
        }
    }
}