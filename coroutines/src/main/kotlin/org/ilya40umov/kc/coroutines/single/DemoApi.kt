package org.ilya40umov.kc.coroutines.single

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlin.random.Random

fun Application.demo() {
    routing {
        route("/demo") {
            get("/random_user_id") {
                val randomId = Random.nextInt(1, Int.MAX_VALUE)
                call.respondText("$randomId")
            }
            get("/user_name") {
                val userId = call.request.queryParameters["id"]?.toInt()
                when (userId) {
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