package org.ilya40umov.kc.classic.combine

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.function.Supplier

@RestController
@RequestMapping("/combine/v5")
class CombineResultsV5(
    private val demoApiClient: DemoApiClient,
    private val executor: ExecutorService
) {

    private fun <V> ExecutorService.computeAsync(
        call: () -> V
    ): CompletableFuture<V> = CompletableFuture.supplyAsync(
        Supplier { call() },
        this
    )

    // Spring MVC supports returning CompletableFuture
    // to unblock the thread until the result is ready
    @GetMapping
    fun combineAsyncResults(): CompletableFuture<String> =
        executor.computeAsync {
            demoApiClient.getRandomUserId()
        }.thenApply { userId ->
            userId to demoApiClient.getUserName(userId)
        }.thenApply { (userId, userName) ->
            "#$userId: $userName\n"
        }
}