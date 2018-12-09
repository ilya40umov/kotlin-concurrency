package org.ilya40umov.kc.classic.jmm

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.concurrent.thread

@RestController
@RequestMapping("/jmm/v2")
class JmmTesterV2 {

    // now it's actually immutable ('val' ==> 'final' in bytecode)
    class ImmutableHolder(
        private val a: Long,
        private val b: Long
    ) {
        fun getA() = a
        fun getB() = b
    }

    // BTW making this field @Volatile also SEEMS to fix the problem with visibility
    // but at any rate, is it the only problem with this code?
    private var value = ImmutableHolder(1, 1)

    @PostMapping
    fun incrementAndCheckValue(): String {
        thread(isDaemon = true) {
            val locRef = value
            value = ImmutableHolder(a = locRef.getA() + 1, b = locRef.getB() + 1)
        }.join()
        val locRef = value
        val a = locRef.getA()
        val b = locRef.getB()
        if (a == 0L || a != b) {
            throw RuntimeException("Oops!!! A: $a, B: $b")
        }
        return "A: $a, B: $b \n"
    }
}