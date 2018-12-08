package org.ilya40umov.kc.reactive.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.PoisonPill
import org.ilya40umov.kc.reactive.actors.DemoApiAccessor.Companion.RandomUserId
import org.ilya40umov.kc.reactive.actors.DemoApiAccessor.Companion.RetrieveRandomUserId
import org.ilya40umov.kc.reactive.actors.DemoApiAccessor.Companion.RetrieveUserName
import org.ilya40umov.kc.reactive.actors.DemoApiAccessor.Companion.RetrievedUserName
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class SingleWinnerChooser(
    @Qualifier("demoApiAccessManagerRef") private val demoApiAccessManager: ActorRef
) : AbstractActor() {

    private lateinit var originator: ActorRef
    private var userId: Int = 0

    override fun createReceive(): Receive =
        receiveBuilder()
            .match(ChooseWinner::class.java) {
                originator = sender
                demoApiAccessManager.tell(RetrieveRandomUserId, self)
            }
            .match(RandomUserId::class.java) { (userId) ->
                this.userId = userId
                demoApiAccessManager.tell(RetrieveUserName(userId), self)
            }
            .match(RetrievedUserName::class.java) { (userName) ->
                originator.tell(WinnerDetails("#$userId: $userName"), self)
                self.tell(PoisonPill.getInstance(), self)
            }
            .build()

    companion object {
        object ChooseWinner
        data class WinnerDetails(val details: String)
    }
}