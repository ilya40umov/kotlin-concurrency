import org.apache.tools.ant.taskdefs.condition.Os
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.4" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    id("io.ktor.plugin") version "2.3.12" apply false
    kotlin("jvm") version "2.0.0" apply false
    kotlin("plugin.spring") version "2.0.0" apply false
}

allprojects {
    group = "me.ilya40umov.kc"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply<JavaPlugin>()
    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        "implementation"("io.github.oshai:kotlin-logging-jvm:6.0.9")
        val kotestVersion = "5.9.1"
        "implementation"(platform("org.junit:junit-bom:5.10.3"))
        "testImplementation"("io.kotest:kotest-runner-junit5:$kotestVersion")
        "testImplementation"("io.kotest:kotest-assertions-core:$kotestVersion")
        "testImplementation"("org.junit.jupiter:junit-jupiter-api")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine")
        if (Os.isFamily(Os.FAMILY_MAC)) {
            "runtimeOnly"("io.netty:netty-resolver-dns-native-macos:4.1.108.Final:osx-aarch_64")
        }
    }
    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs += "-Xjsr305=strict"
                jvmTarget = "17"
            }
        }
        withType<Test> {
            useJUnitPlatform {
                testLogging.showStandardStreams = true
            }
        }
    }
}