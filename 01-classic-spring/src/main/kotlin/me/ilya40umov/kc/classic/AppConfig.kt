package me.ilya40umov.kc.classic

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
class AppConfig {
    @Bean
    fun executor(): ExecutorService =
        Executors.newCachedThreadPool()

    @Bean
    fun restTemplate(): RestTemplate =
        RestTemplate()
}