package org.ilya40umov.kc.classic.combine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.function.Supplier

@RestController
@RequestMapping("/combine/v4")
class CombineResultsV4(
    @Autowired private val restTemplate: RestTemplate,
    @Autowired private val executor: ExecutorService
) {

    private fun getRandomUserId(): Int =
        restTemplate.getForEntity<String>("http://127.0.0.1:7070/demo/random_user_id").body?.toInt() ?: -1

    private fun getUserName(userId: Int): String =
        restTemplate.getForEntity<String>("http://127.0.0.1:7070/demo/user_name?id=$userId").body ?: "n/a"

    private fun <V> ExecutorService.computeAsync(call: () -> V): CompletableFuture<V> =
        CompletableFuture.supplyAsync(Supplier { call() }, this)

    @GetMapping
    fun combineAsyncResults(): String {
        val futureResult =
            executor.computeAsync {
                getRandomUserId()
            }.thenApplyAsync { userId ->
                // only called when the first call is finished
                userId to getUserName(userId)
            }.thenApply { (userId, userName) ->
                "#$userId: $userName\n"
            }
        // wait for the value to become available
        return futureResult.get()
    }
}