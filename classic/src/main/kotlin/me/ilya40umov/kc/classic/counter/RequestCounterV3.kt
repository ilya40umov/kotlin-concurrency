package me.ilya40umov.kc.classic.counter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/counter/v3")
class RequestCounterV3 {

    // volatile so that we can safely read it in "getValue()"
    @Volatile
    private var counter = 0

    @GetMapping
    fun getValue() = "$counter\n"

    // synchronized to prevent race conditions, but can we do better?
    @Synchronized
    @PostMapping
    fun incrementValue() = "${++counter}\n"
}