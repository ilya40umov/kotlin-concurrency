package org.ilya40umov.kc.reactive.actors

import akka.actor.AbstractActor
import org.ilya40umov.kc.reactive.actors.DemoApiAccessManager.Companion.GimmeWork
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class DemoApiAccessor : AbstractActor() {

    // TODO implement demo api accessor

    override fun preStart() {
        context.parent.tell(GimmeWork, self)
    }

    override fun createReceive(): Receive =
        receiveBuilder()
            .match(RetrieveRandomUserId::class.java) {
                sender.tell(RandomUserId(userId = 123), self)
                context.parent.tell(GimmeWork, self)
            }
            .match(RetrieveUserName::class.java) {
                sender.tell(RetrievedUserName("Iron Man"), self)
                context.parent.tell(GimmeWork, self)
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