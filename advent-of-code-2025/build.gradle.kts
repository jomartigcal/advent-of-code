plugins {
    kotlin("jvm") version "2.2.20"
}

group = "com.tigcal.projects.adventofcode"
version = "2025.0-SNAPSHOT"

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
    jvmToolchain(21)
}