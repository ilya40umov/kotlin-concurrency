package org.ilya40umov.kc.reactive.akka.helper

import akka.actor.ActorRef
import akka.actor.ActorSystem
import org.ilya40umov.kc.reactive.akka.actors.DemoApiAccessManager
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AkkaConfig {

    @Bean
    fun actorSystem(applicationContext: ApplicationContext): ActorSystem =
        ActorSystem.create("reactive-demo").also { system ->
            SpringExtension.get(system).also { extension ->
                extension.initialize(applicationContext)
                // TODO create access manager
            }
        }

    @Bean
    fun demoApiAccessManagerRef(actorSystem: ActorSystem): ActorRef =
        actorSystem.actorOf(DemoApiAccessManager::class, "demo-api-access-manager")
}