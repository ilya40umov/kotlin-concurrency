package me.ilya40umov.kc.coroutines.single

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlin.random.Random

fun Application.demo() {
    routing {
        route("/demo") {
            get("/random_user_id") {
                val randomId = Random.nextInt(1, Int.MAX_VALUE)
                call.respondText("$randomId")
            }
            get("/user_name") {
                when (val userId = call.request.queryParameters["id"]?.toInt()) {
                    null -> call.respondText(
                        text = "Param `id` is missing!",
                        status = HttpStatusCode.BadRequest
                    )
                    else -> {
                        val userName = if (userId % 2 == 0) "Jack" else "Jill"
                        call.respondText { userName }
                    }
                }
            }
        }
    }
}