package me.ilya40umov.consumer

import kotlinx.coroutines.delay
import java.util.*

class SqsClient {
    suspend fun readBatch(): List<String> {
        delay(1000L)
        return (1..5).map { "$it---${UUID.randomUUID()}" }.toList()
    }

    suspend fun removeMsg(msg: String) {
        delay(50L)
        println("Removing message $msg from SQS queue.")
    }
}