package me.ilya40umov.kc.classic

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice(basePackages = ["me.ilya40umov.kc.classic"])
class ErrorAdvice {

    private val logger = KotlinLogging.logger {}

    @ResponseBody
    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAnyError(e: Throwable): String {
        logger.error { "Caught ${e.javaClass.name} ${e.message}" }
        return "${e.javaClass.name} ${e.message}"
    }

}