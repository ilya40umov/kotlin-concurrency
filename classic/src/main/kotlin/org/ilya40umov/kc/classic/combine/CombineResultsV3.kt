package org.ilya40umov.kc.classic.combine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

@RestController
@RequestMapping("/combine/v3")
class CombineResultsV3(
    @Autowired private val restTemplate: RestTemplate,
    @Autowired private val executor: ExecutorService
) {

    private fun getRandomUserId(): Int =
        restTemplate.getForEntity<String>("http://127.0.0.1:7070/demo/random_user_id").body?.toInt() ?: -1

    private fun getUserName(userId: Int): String =
        restTemplate.getForEntity<String>("http://127.0.0.1:7070/demo/user_name?id=$userId").body ?: "n/a"

    @GetMapping
    fun combineAsyncResults(): String {
        val userId: Future<Int> = executor.submit<Int> { getRandomUserId() }
        val userName: Future<String> = executor.submit<String> { getUserName(userId.get()) }
        // with java.unit.concurrent.Future you can only call a blocking .get() method
        return "#${userId.get()}: ${userName.get()}\n"
    }
}