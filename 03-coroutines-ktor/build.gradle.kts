plugins {
    application
    kotlin("jvm")
    id("io.ktor.plugin")
}

dependencies {
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-html-builder")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-default-headers")
    implementation("io.ktor:ktor-client-apache-jvm")

    implementation("ch.qos.logback:logback-classic:1.5.6")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.1")
    implementation("io.projectreactor.netty:reactor-netty:1.1.20")
}

application {
    mainClass = "me.ilya40umov.kc.coroutines.CoroutinesAppKt"
}

ktor {
    fatJar {
        archiveFileName = "app.jar"
    }
}