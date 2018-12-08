package org.ilya40umov.kc.reactive.akka

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorRefFactory
import akka.actor.ActorSystem
import akka.util.Timeout
import scala.compat.java8.FutureConverters
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import java.time.Duration
import java.util.concurrent.CompletionStage
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

fun ActorSystem.actorOf(actorClass: KClass<out Actor>, name: String): ActorRef =
    this.actorOf(SpringExtension.get(this).props(actorClass), name)

fun ActorRefFactory.actorOf(actorClass: KClass<out Actor>, name: String): ActorRef =
    this.actorOf(SpringExtension.get(this.systemImpl()).props(actorClass), name)

fun Duration.toTimeout() = Timeout.durationToTimeout(FiniteDuration(this.toMillis(), TimeUnit.MILLISECONDS))!!

fun <V> ActorRef.ask(msg: Any, duration: Duration = Duration.ofSeconds(30)): CompletionStage<V> {
    @Suppress("UNCHECKED_CAST")
    val future = akka.pattern.Patterns.ask(this, msg, duration.toTimeout()) as Future<V>
    return FutureConverters.toJava(future)
}
