import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

group = "net.riedel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("no.tornado:tornadofx:1.7.19")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-M2")
    compile("org.jsoup:jsoup:1.12.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}