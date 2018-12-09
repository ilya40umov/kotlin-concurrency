package org.ilya40umov.kc.classic.combine

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Phaser
import kotlin.concurrent.thread

@RestController
@RequestMapping("/combine/v2")
class CombineResultsV2(
    private val demoApiClient: DemoApiClient
) {

    // using Phaser - difficult to understand
    @GetMapping
    fun combineAsyncResults(): String {
        val phaser = Phaser(1 + 2)
        var userId: Int? = null
        var userName: String? = null
        thread(isDaemon = true) {
            userId = demoApiClient.getRandomUserId()
            phaser.arriveAndDeregister()
        }
        thread(isDaemon = true) {
            phaser.arriveAndAwaitAdvance()
            userName = demoApiClient.getUserName(userId!!)
            phaser.arriveAndDeregister()
        }
        repeat(2) { phaser.arriveAndAwaitAdvance() }
        return "#$userId: $userName\n"
    }
}