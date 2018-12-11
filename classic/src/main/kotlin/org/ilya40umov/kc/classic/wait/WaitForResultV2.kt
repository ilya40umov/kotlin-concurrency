package org.ilya40umov.kc.classic.wait

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

@RestController
@RequestMapping("/wait/v2")
class WaitForResultV2 {

    // java.util.concurrent has better alternatives to wait/notify
    @GetMapping
    fun calculate(): String {
        val latch = CountDownLatch(1)
        var value = 0
        thread(isDaemon = true) {
            Thread.sleep(0L)
            value = 42
            latch.countDown()
        }
        latch.await()
        if (value != 42) {
            throw RuntimeException("Oops: the value is NOT forty two!")
        }
        return "$value \n"
    }
}