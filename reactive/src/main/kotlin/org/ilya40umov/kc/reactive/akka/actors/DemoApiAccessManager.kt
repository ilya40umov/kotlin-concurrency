package org.ilya40umov.kc.reactive.akka.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import mu.KLogging
import org.ilya40umov.kc.reactive.akka.actors.DemoApiAccessor.Companion.DemoApiWork
import org.ilya40umov.kc.reactive.akka.helper.actorOf
import org.ilya40umov.kc.reactive.akka.helper.toDeadline
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import scala.concurrent.duration.Deadline
import java.time.Duration
import java.util.LinkedList

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class DemoApiAccessManager : AbstractActor() {

    private val workerQueue = LinkedList<ActorRef>()
    private val requestQueue = LinkedList<WorkRequest>()

    override fun createReceive(): Receive =
        receiveBuilder()
            .match(GimmeWork::class.java) { gimmeWorkHandler() }
            .match(DemoApiWork::class.java, this::incomingWorkHandler)
            .build()

    override fun preStart() {
        repeat(100) { idx ->
            context.actorOf(
                DemoApiAccessor::class,
                "demo-api-accessor-$idx"
            )
        }
    }

    private fun gimmeWorkHandler() {
        val worker = sender
        var suitableTaskFound = false
        while (requestQueue.isNotEmpty()) {
            val (msg, deadline, replyTo) = requestQueue.pop()
            if (deadline.isOverdue) {
                logger.warn { "Had to drop an overdue task: $msg" }
                continue
            } else {
                suitableTaskFound = true
                worker.tell(msg, replyTo)
            }
        }
        if (!suitableTaskFound) {
            workerQueue.push(sender)
        }
    }

    private fun incomingWorkHandler(demoApiWork: DemoApiWork) {
        if (workerQueue.isEmpty()) {
            requestQueue.push(
                WorkRequest(
                    msg = demoApiWork,
                    deadline = Duration.ofSeconds(10).toDeadline(),
                    requester = sender
                )
            )
        } else {
            workerQueue.pop().also { worker ->
                worker.tell(demoApiWork, sender)
            }
        }
    }

    companion object : KLogging() {
        object GimmeWork
        private data class WorkRequest(
            val msg: Any,
            val deadline: Deadline,
            val requester: ActorRef
        )
    }
}