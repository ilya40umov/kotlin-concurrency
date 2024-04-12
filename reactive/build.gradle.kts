plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.4")

    implementation("com.typesafe.akka:akka-actor_3:2.8.5")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
