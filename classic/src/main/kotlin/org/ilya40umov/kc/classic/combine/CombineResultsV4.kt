package org.ilya40umov.kc.classic.combine

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.function.Supplier

@RestController
@RequestMapping("/combine/v4")
class CombineResultsV4(
    private val demoApiClient: DemoApiClient,
    private val executor: ExecutorService
) {

    // most Java APIs are very verbose
    // and require extensions to reduce boilerplate
    private fun <V> ExecutorService.computeAsync(
        call: () -> V
    ): CompletableFuture<V> = CompletableFuture.supplyAsync(
        Supplier { call() },
        this
    )

    // Now using CompletableFuture, yay!
    // However, we still block Tomcat thread...
    @GetMapping
    fun combineAsyncResults(): String {
        val futureResult =
            executor.computeAsync {
                demoApiClient.getRandomUserId()
            }.thenApplyAsync { userId ->
                // only called when the first call is finished
                userId to demoApiClient.getUserName(userId)
            }.thenApply { (userId, userName) ->
                "#$userId: $userName\n"
            }
        // wait for the value to become available
        return futureResult.get()
    }
}