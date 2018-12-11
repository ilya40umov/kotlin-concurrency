package org.ilya40umov.kc.classic.jmm

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

@RestController
@RequestMapping("/jmm/v3")
class JmmTesterV3 {

    class ImmutableHolder(
        private val a: Long,
        private val b: Long
    ) {
        fun getA() = a
        fun getB() = b
    }

    // atomics are build around CAS operations
    private val value = AtomicReference(ImmutableHolder(1, 1))

    @PostMapping
    fun incrementAndCheckValue(): String {
        thread(isDaemon = true) {
            do {
                val locRef = this.value.get()
                val newRef = ImmutableHolder(
                    a = locRef.getA() + 1,
                    b = locRef.getB() + 1
                )
            } while (!value.compareAndSet(locRef, newRef))
        }.join()
        val locRef = this.value.get()
        val a = locRef.getA()
        val b = locRef.getB()
        if (a == 0L || a != b) {
            throw RuntimeException("Oops!!! A: $a, B: $b")
        }
        return "A: $a, B: $b \n"
    }
}