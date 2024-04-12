package me.ilya40umov.kc.reactive.akka.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Cancellable
import akka.actor.PoisonPill
import io.github.oshai.kotlinlogging.KotlinLogging
import me.ilya40umov.kc.reactive.akka.actors.DemoApiAccessor.Companion.RandomUserId
import me.ilya40umov.kc.reactive.akka.actors.DemoApiAccessor.Companion.RetrieveRandomUserId
import me.ilya40umov.kc.reactive.akka.actors.DemoApiAccessor.Companion.RetrieveUserName
import me.ilya40umov.kc.reactive.akka.actors.DemoApiAccessor.Companion.RetrievedUserName
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.time.Duration

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class SingleWinnerChooser(
    @Qualifier("demoApiAccessManagerRef") private val demoApiAccessManager: ActorRef
) : AbstractActor() {

    private val logger = KotlinLogging.logger {}

    private lateinit var originator: ActorRef
    private var obtainedUserId: Int? = null
    private lateinit var selfShutdown: Cancellable

    override fun preStart() {
        selfShutdown = context.system.scheduler.scheduleOnce(
            Duration.ofSeconds(30),
            {
                logger.info { "Cleaning up a 'stuck' winner chooser..." }
                self.tell(PoisonPill.getInstance(), self)
            },
            context.dispatcher()
        )
    }

    override fun createReceive(): Receive =
        receiveBuilder()
            .match(ChooseWinner::class.java) {
                originator = sender
                demoApiAccessManager.tell(RetrieveRandomUserId, self)
            }
            .match(RandomUserId::class.java) { (userId) ->
                obtainedUserId = userId
                demoApiAccessManager.tell(RetrieveUserName(userId), self)
            }
            .match(RetrievedUserName::class.java) { (userName) ->
                originator.tell(
                    WinnerDetails("#$obtainedUserId: $userName"),
                    self
                )
                selfShutdown.cancel()
                self.tell(PoisonPill.getInstance(), self)
            }
            .build()

    companion object {
        object ChooseWinner
        data class WinnerDetails(val details: String)
    }
}