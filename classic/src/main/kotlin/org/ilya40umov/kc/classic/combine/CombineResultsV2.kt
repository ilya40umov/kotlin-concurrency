package org.ilya40umov.kc.classic.combine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Phaser

@RestController
@RequestMapping("/combine/v2")
class CombineResultsV2(
    @Autowired private val restTemplate: RestTemplate,
    @Autowired private val executor: ExecutorService
) {

    private fun getRandomUserId(): Int =
        restTemplate.getForEntity<String>("http://127.0.0.1:8080/demo/random_user_id").body?.toInt() ?: -1

    private fun getUserName(userId: Int): String =
        restTemplate.getForEntity<String>("http://127.0.0.1:8080/demo/user_name?id=$userId").body ?: "n/a"

    @GetMapping
    fun combineAsyncResults(): String {
        val phaser = Phaser(1 + 2)
        var userId = 0
        var userName = ""
        executor.submit {
            userId = getRandomUserId()
            phaser.arriveAndDeregister()
        }
        executor.submit {
            phaser.arriveAndAwaitAdvance()
            userName = getUserName(userId)
            phaser.arriveAndDeregister()
        }
        repeat(2) { phaser.arriveAndAwaitAdvance() }
        return "#$userId: $userName\n"
    }
}