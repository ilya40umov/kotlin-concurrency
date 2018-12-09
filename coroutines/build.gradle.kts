import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.ilya40umov.kc"
version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
}

plugins {
    application
    val kotlinVersion = "1.3.10"
    kotlin("jvm") version kotlinVersion
}

application {
    mainClassName = "org.ilya40umov.kc.coroutines.CoroutinesAppKt"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.ktor:ktor-server-netty:1.0.1")
    compile("io.ktor:ktor-html-builder:1.0.1")
    compile("io.ktor:ktor-client-apache:1.0.1")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("io.github.microutils:kotlin-logging:1.6.22")
}