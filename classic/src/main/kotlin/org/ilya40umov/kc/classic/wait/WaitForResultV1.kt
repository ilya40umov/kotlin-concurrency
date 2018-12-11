package org.ilya40umov.kc.classic.wait

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.concurrent.thread

@RestController
@RequestMapping("/wait/v1")
class WaitForResultV1 {

    // using wait() / notify() is not error prone at all
    @GetMapping
    fun calculate(): String {
        val lock = Object()
        var value = 0
        thread(isDaemon = true) {
            synchronized(lock) {
                Thread.sleep(0L)
                value = 42
                lock.notify()
            }
        }
        synchronized(lock) {
            lock.wait()
            if (value != 42) {
                throw RuntimeException("Oops: the value is NOT forty two!")
            }
            return "$value \n"
        }
    }
}