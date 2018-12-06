package org.ilya40umov.kc.classic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClassicConcurrencyApplication

fun main(args: Array<String>) {
    runApplication<ClassicConcurrencyApplication>(*args)
}
