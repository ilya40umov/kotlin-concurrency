package org.ilya40umov.kc.classic.combine

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@Component
class DemoApiClient(
    private val restTemplate: RestTemplate
) {
    fun getRandomUserId(): Int =
        restTemplate
            .getForEntity<String>(
                "http://127.0.0.1:7070/demo/random_user_id")
            .body?.toInt() ?: -1

    fun getUserName(userId: Int): String =
        restTemplate
            .getForEntity<String>(
                "http://127.0.0.1:7070/demo/user_name?id=$userId")
            .body ?: "n/a"
}