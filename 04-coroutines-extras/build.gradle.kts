plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.8.1")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.7")
}