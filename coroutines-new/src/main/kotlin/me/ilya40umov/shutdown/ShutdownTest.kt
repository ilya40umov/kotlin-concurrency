package me.ilya40umov.shutdown

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    val job1 = GlobalScope.launch {
        while(isActive) {
            delay(500)
            println("<><><> Finished chunk...")
        }
    }
    val job2 = GlobalScope.launch {
        while(isActive) {
            // normally you don't want to immediately terminate an important computation
            withContext(NonCancellable) {
                delay(3000)
            }
            println("[][][] Finished big chunk...")
        }
    }
    val coordinator = ShutdownCoordinator()
    coordinator.addJobsToCancel(listOf(job1, job2))
    coordinator.addShutdownHook()
    coordinator.awaitShutdown()
}