package org.ilya40umov.kc.coroutines.counter

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.counterV1() {
    // coroutines aren't threads, now I should be safe, right?
    var counter = 0
    routing {
        route("/counter") {
            get("/v1") {
                call.respondText("$counter\n")
            }
            post("/v1") {
                call.respondText { "${++counter}\n" }
            }
        }
    }
}