package me.ilya40umov.kc.classic.counter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/counter/v1")
class RequestCounterV1 {

    // troll: shared mutable state - what can possible go wrong?
    private var counter = 0

    @GetMapping
    fun getValue() = "$counter\n"

    @PostMapping
    fun incrementValue() = "${++counter}\n"
}