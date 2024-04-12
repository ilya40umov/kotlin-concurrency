package me.ilya40umov.kc.classic.combine

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

@RestController
@RequestMapping("/combine/v1")
class CombineResultsV1(
    private val demoApiClient: DemoApiClient
) {

    // using multiple CountDownLatch instances - very verbose
    @GetMapping
    fun combineAsyncResults(): String {
        val userIdLatch = CountDownLatch(1)
        val userNameLatch = CountDownLatch(1)
        var userId: Int? = null
        var userName: String? = null
        thread(isDaemon = true) {
            userId = demoApiClient.getRandomUserId()
            userIdLatch.countDown()
        }
        thread(isDaemon = true) {
            userIdLatch.await()
            userName = demoApiClient.getUserName(userId!!)
            userNameLatch.countDown()
        }
        userNameLatch.await()
        return "#$userId: $userName\n"
    }
}