import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.ilya40umov.kc"
version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
}

plugins {
    val kotlinVersion = "1.3.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.1.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
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
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("org.springframework.boot:spring-boot-devtools")
    compile("com.typesafe.akka:akka-actor_2.12:2.5.22")
    compile("org.scala-lang.modules:scala-java8-compat_2.12:0.9.0")
    compile("io.github.microutils:kotlin-logging:1.6.26")
}
