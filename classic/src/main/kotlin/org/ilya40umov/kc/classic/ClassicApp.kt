package org.ilya40umov.kc.classic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootApplication
class ClassicApp {

    @Bean
    fun executor(): ExecutorService =
        Executors.newCachedThreadPool()

    @Bean
    fun restTemplate(): RestTemplate =
        RestTemplate()
}

fun main(args: Array<String>) {
    runApplication<ClassicApp>(*args)
}
