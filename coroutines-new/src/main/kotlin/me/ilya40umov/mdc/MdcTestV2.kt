@file:Suppress("DuplicatedCode")

package me.ilya40umov.mdc

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@Suppress("DuplicatedCode")
fun main() {
    runBlocking {
        logger.info { "Started." }
        listOf("123", "124", "125").map { userId ->
            GlobalScope.launch(MDCContext(mapOf("user_id" to userId))) {
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