package org.ilya40umov.kc.reactive.akka.helper

import akka.actor.AbstractExtensionId
import akka.actor.Actor
import akka.actor.ExtendedActorSystem
import akka.actor.Extension
import akka.actor.Props
import org.springframework.context.ApplicationContext
import kotlin.reflect.KClass

object SpringExtension : AbstractExtensionId<SpringExtension.SpringExt>() {

    override fun createExtension(system: ExtendedActorSystem): SpringExt {
        return SpringExt()
    }

    class SpringExt : Extension {

        @Volatile
        private var applicationContext: ApplicationContext? = null

        fun initialize(applicationContext: ApplicationContext) {
            this.applicationContext = applicationContext
        }

        fun props(actorClass: KClass<out Actor>): Props {
            return Props.create(
                SpringActorProducer::class.java,
                applicationContext,
                actorClass
            )
        }
    }
}