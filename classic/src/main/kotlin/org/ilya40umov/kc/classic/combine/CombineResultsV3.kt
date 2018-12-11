package org.ilya40umov.kc.classic.combine

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

@RestController
@RequestMapping("/combine/v3")
class CombineResultsV3(
    private val demoApiClient: DemoApiClient,
    private val executor: ExecutorService
) {

    // using ExecutorService & Future - more concise
    // BUT Java's Future lacks "combinators"
    // => you can only call .get() that blocks
    @GetMapping
    fun combineAsyncResults(): String {
        val userId: Future<Int> = executor.submit<Int> {
            demoApiClient.getRandomUserId()
        }
        val userName: Future<String> = executor.submit<String> {
            demoApiClient.getUserName(userId.get())
        }
        // with this Future you only get a blocking .get() method
        return "#${userId.get()}: ${userName.get()}\n"
    }
}