package org.ilya40umov.kc.classic

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice(basePackages = ["org.ilya40umov.kc.classic"])
class ErrorAdvice {

    @ResponseBody
    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAnyError(e: Throwable): String {
        println("${e.javaClass.name} ${e.message}")
        return "${e.javaClass.name} ${e.message}"
    }
}