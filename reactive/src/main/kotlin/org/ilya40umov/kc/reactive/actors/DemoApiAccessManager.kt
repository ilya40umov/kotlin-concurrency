package org.ilya40umov.kc.reactive.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import org.ilya40umov.kc.reactive.actors.DemoApiAccessor.Companion.DemoApiWork
import org.ilya40umov.kc.reactive.akka.actorOf
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.LinkedList

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class DemoApiAccessManager : AbstractActor() {

    private val workerQueue = LinkedList<ActorRef>()
    private val requestQueue = LinkedList<Pair<Any, ActorRef>>()

    override fun createReceive(): Receive =
        receiveBuilder()
            .match(GimmeWork::class.java) {
                if (requestQueue.isEmpty()) {
                    workerQueue.push(sender)
                } else {
                    requestQueue.pop().also { (msg, replyTo) ->
                        sender.tell(msg, replyTo)
                    }
                }
            }
            .match(DemoApiWork::class.java) { msg ->
                if (workerQueue.isEmpty()) {
                    requestQueue.push(msg to sender)
                } else {
                    workerQueue.pop().also { worker ->
                        worker.tell(msg, sender)
                    }
                }
            }
            .build()

    override fun preStart() {
        repeat(5) { idx ->
            context.actorOf(DemoApiAccessor::class, "demo-api-accessor-$idx")
        }
    }

    companion object {
        object GimmeWork
    }
}