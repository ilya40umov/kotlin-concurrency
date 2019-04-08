# kotlin-concurrency
This project is a collection of mini projects written in Kotlin,
which show examples of handling various concurrency problems in the backend code.

#### Classic
Under [classic](https://github.com/ilya40umov/kotlin-concurrency/tree/master/classic)
you can find examples of approaches classically used in Java's, as well as their potential gotchas.
(A lot of this code is intentionally miswritten).

#### Reactive
[reactive](https://github.com/ilya40umov/kotlin-concurrency/tree/master/reactive)
contains examples of using Spring's WebFlux and Akka's actors for handling concurrency.

#### Coroutines
The project [coroutines](https://github.com/ilya40umov/kotlin-concurrency/tree/master/coroutines)
shows Kotlin's own (and preferred) model of handling concurrency, 
which is based on the concept of suspending functions and `kotlinx.coroutines` library.
