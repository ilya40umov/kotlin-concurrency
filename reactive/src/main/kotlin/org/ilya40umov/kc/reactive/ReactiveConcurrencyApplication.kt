package org.ilya40umov.kc.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveConcurrencyApplication

fun main(args: Array<String>) {
    runApplication<ReactiveConcurrencyApplication>(*args)
}
