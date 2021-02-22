package me.ilya40umov.shutdown

import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch

class ShutdownCoordinator {

    private val jobsToCancel = CopyOnWriteArrayList<Job>()
    private val shutdownCountdown = CountDownLatch(1)

    fun addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                runBlocking {
                    jobsToCancel.map { it.cancel() }
                    jobsToCancel.joinAll()
                }
                shutdownCountdown.countDown()
            }
        })
    }

    fun addJobsToCancel(jobs: List<Job>) {
        jobsToCancel.addAllAbsent(jobs)
    }

    fun awaitShutdown() {
        shutdownCountdown.await()
    }
}