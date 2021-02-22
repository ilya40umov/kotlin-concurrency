package me.ilya40umov.consumer

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

class SqsConsumer(
    private val sqsClient: SqsClient
) {

    private val scope = CoroutineScope(SupervisorJob())
    private val channel = Channel<String>()

    fun start(): Job = scope.launch {
        startPoller()
        repeat(5) {
            startProcessor()
        }
    }

    private fun startPoller(): Job = scope.launch {
        repeatUntilCancelled {
            sqsClient.readBatch().forEach { msg ->
                channel.send(msg)
            }
        }
    }

    private fun startProcessor(): Job = scope.launch {
        repeatUntilCancelled {
            for (msg in channel) {
                withContext(NonCancellable) {
                    // you probably don't want to let this processing to be terminated abruptly
                    sqsClient.removeMsg(msg)
                }
            }
        }
    }

    private suspend fun CoroutineScope.repeatUntilCancelled(block: suspend () -> Unit) {
        while (isActive) {
            try {
                block()
                yield()
            } catch (e: CancellationException) {
                println("Coroutine cancelled.")
            } catch (e: Exception) {
                println("Provided block failed.")
            }
        }
    }
}