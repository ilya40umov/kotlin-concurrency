package me.ilya40umov.kc.classic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClassicApp

fun main(args: Array<String>) {
    runApplication<ClassicApp>(*args)
}
