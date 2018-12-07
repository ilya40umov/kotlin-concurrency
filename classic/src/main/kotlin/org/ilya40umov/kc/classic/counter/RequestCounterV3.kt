package org.ilya40umov.kc.classic.counter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/counter/v3")
class RequestCounterV3 {

    private var counter = 0

    @Synchronized
    @GetMapping
    fun getValue() = "$counter\n"

    @PostMapping
    fun incrementValue() = synchronized(this) { "${++counter}\n" }
}