package org.ilya40umov.kc.classic.combine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

@RestController
@RequestMapping("/combine/v1")
class CombineResultsV1(
    @Autowired private val restTemplate: RestTemplate
) {

    private fun getRandomUserId(): Int =
        restTemplate.getForEntity<String>("http://127.0.0.1:8080/demo/random_user_id").body?.toInt() ?: -1

    private fun getUserName(userId: Int): String =
        restTemplate.getForEntity<String>("http://127.0.0.1:8080/demo/user_name?id=$userId").body ?: "n/a"

    @GetMapping
    fun combineAsyncResults(): String {
        val userIdLatch = CountDownLatch(1)
        val userNameLatch = CountDownLatch(1)
        var userId = 0
        var userName = ""
        thread(isDaemon = true) {
            userId = getRandomUserId()
            userIdLatch.countDown()
        }
        thread(isDaemon = true) {
            userIdLatch.await()
            userName = getUserName(userId)
            userNameLatch.countDown()
        }
        userNameLatch.await()
        return "#$userId: $userName\n"
    }
}