@file:Suppress("DuplicatedCode")

package me.ilya40umov.mdc

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.slf4j.MDC

private val logger = KotlinLogging.logger {}

fun main() {
    runBlocking {
        logger.info { "Started." }
        listOf("123", "124", "125").map { userId ->
            MDC.put("user_id", userId)
            GlobalScope.launch {
                logger.info { "Signed in." }
                listOf(
                    launch { logger.info { "Checked balance." } },
                    launch { logger.info { "Topped up the account." } }
                )
                logger.info { "Signed out." }
            }
        }.joinAll()
        logger.info { "Finished." }
    }
}