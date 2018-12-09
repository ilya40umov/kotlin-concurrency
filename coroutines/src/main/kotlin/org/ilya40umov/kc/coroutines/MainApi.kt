package org.ilya40umov.kc.coroutines

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.html.respondHtml
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)

    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"Coroutines Demo App" }
                }
                body {
                    p {
                        +"Hello from Coroutines Demo App!"
                    }
                }
            }
        }
    }
}
