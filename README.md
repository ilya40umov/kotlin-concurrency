# kotlin-concurrency
This project is a collection of mini code examples written in Kotlin,
which show how various concurrency problems can be handled in the backend code.

#### Classic
Under [01-classic-spring](/01-classic-spring)
you can find examples of approaches classically used in Java's, as well as their potential gotchas.
(A lot of this code is intentionally written incorrectly).

#### Reactive
[02-reactive-spring](/02-reactive-spring)
contains examples of using Spring's WebFlux and Akka's actors for handling concurrency.

#### Coroutines
The project [03-coroutines-ktor](/03-coroutines-ktor)
shows Kotlin's own (and preferred) model of handling concurrency, 
which is based on the concept of suspending functions and `kotlinx.coroutines` library.

[04-coroutines-extra](/04-coroutines-extras) 
contains even more examples of using coroutines for solving various problems.