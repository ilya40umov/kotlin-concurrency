package org.ilya40umov.kc.reactive.akka.helper

import akka.actor.Actor
import akka.actor.IndirectActorProducer
import org.springframework.context.ApplicationContext
import kotlin.reflect.KClass

class SpringActorProducer(
    private val applicationContext: ApplicationContext,
    private val actorClass: KClass<out Actor>
) : IndirectActorProducer {

    override fun produce(): Actor {
        return applicationContext.getBean(actorClass.java)
    }

    override fun actorClass(): Class<out Actor> {
        return actorClass.java
    }
}