package me.ilya40umov.kc.coroutines.friends

import kotlinx.coroutines.delay
import java.lang.RuntimeException
import java.util.concurrent.atomic.AtomicInteger

class FakeService {
    private val dbCallsCount = AtomicInteger()

    suspend fun getUserById(userId: Long): User {
        if (userId == 999L) {
            delay(50L)
            throw RuntimeException("Fake exception")
        }
        repeat(5) { delay(100L) }
        dbCallsCount.incrementAndGet()
        return User(
            userId = userId,
            name = "Person$userId",
            friendIds = listOf(101, 102, 103, 104, 105)
        )
    }

    fun getDbCallsCount(): Int = dbCallsCount.get()
}