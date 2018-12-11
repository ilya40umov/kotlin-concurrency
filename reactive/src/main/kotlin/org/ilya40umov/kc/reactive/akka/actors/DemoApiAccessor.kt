package org.ilya40umov.kc.reactive.akka.actors

import akka.actor.AbstractActor
import org.ilya40umov.kc.reactive.akka.actors.DemoApiAccessManager.Companion.GimmeWork
import org.ilya40umov.kc.reactive.demo.DemoApiClient
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class DemoApiAccessor(
    private val demoApiClient: DemoApiClient
) : AbstractActor() {

    override fun preStart() {
        context.parent.tell(GimmeWork, self)
    }

    override fun createReceive(): Receive =
        receiveBuilder()
            .match(RetrieveRandomUserId::class.java) {
                val senderRef = sender
                demoApiClient.getRandomUserId()
                    .doOnSuccess { userId ->
                        // ACHTUNG!!!
                        // You can't access actor's internal state in here
                        senderRef.tell(RandomUserId(userId), self)
                        context.parent.tell(GimmeWork, self)
                    }
                    .subscribe()
            }
            .match(RetrieveUserName::class.java) { (userId) ->
                val senderRef = sender
                demoApiClient.getUserName(userId)
                    .doOnSuccess { userName ->
                        // ACHTUNG!!!
                        // You can't access actor's internal state in here
                        senderRef.tell(RetrievedUserName(userName), self)
                        context.parent.tell(GimmeWork, self)
                    }
                    .subscribe()
            }
            .build()

    companion object {
        interface DemoApiWork
        object RetrieveRandomUserId : DemoApiWork
        data class RandomUserId(val userId: Int)
        data class RetrieveUserName(val userId: Int) : DemoApiWork
        data class RetrievedUserName(val userName: String)
    }
}