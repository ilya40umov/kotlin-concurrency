package me.ilya40umov.kc.coroutines.stream

import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.cacheControl
import io.ktor.server.response.respondTextWriter
import kotlinx.coroutines.flow.Flow

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun <T> ApplicationCall.respondSse(eventFlow: Flow<T>) {
    response.cacheControl(CacheControl.NoCache(null))
    respondTextWriter(contentType = ContentType.Text.EventStream) {
        eventFlow.collect { event ->
            write("data: $event\n\n")
            flush()
        }
    }
}