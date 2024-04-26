plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.8.0")
    implementation("ch.qos.logback:logback-classic:1.5.4")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.9")
}