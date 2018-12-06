package org.ilya40umov.kc.classic.counter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/counter/v2")
class RequestCounterV2 {

    @Volatile
    private var counter = 0

    @GetMapping
    fun getValue() = "$counter\n"

    @PostMapping
    fun incrementValue() = "${++counter}\n"
}