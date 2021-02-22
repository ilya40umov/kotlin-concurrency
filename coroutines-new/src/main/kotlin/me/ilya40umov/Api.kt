package me.ilya40umov

import io.ktor.application.*
import io.ktor.features.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.main() {
    install(StatusPages)
}