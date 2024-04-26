package me.ilya40umov.kc.classic.counter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicInteger

@RestController
@RequestMapping("/counter/v4")
class RequestCounterV4 {

    // atomics are based on CAS and more efficient than "synchronized"
    private val counter = AtomicInteger()

    @GetMapping
    fun getValue() = "${counter.get()}\n"

    @PostMapping
    fun incrementValue() = "${counter.incrementAndGet()}\n"
}