plugins {
    kotlin("jvm") version "1.9.20"
}

group = "com.tigcal.projects.adventofcode"
version = "2023.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}