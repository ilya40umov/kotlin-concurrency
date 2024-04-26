package me.ilya40umov.kc.coroutines.counter

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.counterV1() {
    // troll: coroutines aren't threads, now I should be safe, right?
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