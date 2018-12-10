package org.ilya40umov.kc.coroutines.stream

import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.response.header
import io.ktor.response.respondTextWriter
import kotlinx.coroutines.channels.ReceiveChannel

val ContentTypeTextEventStream = ContentType.parse("text/event-stream")
const val SSE_PREFIX = "data: "

suspend fun <T> ApplicationCall.respondSse(events: ReceiveChannel<T>) {
    response.header(HttpHeaders.CacheControl, "no-cache")
    respondTextWriter(contentType = ContentTypeTextEventStream) {
        for (event in events) {
            write("$SSE_PREFIX$event\n\n")
            flush()
        }
    }
}