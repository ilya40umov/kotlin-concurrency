package me.ilya40umov.consumer

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    val sqsConsumer = SqsConsumer(sqsClient = SqsClient())
    val job = sqsConsumer.start()
    runBlocking {
        delay(15000L)
        job.cancel() // ideally you would combine it with ShutdownCoordinator
    }
}