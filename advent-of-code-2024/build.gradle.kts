plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.tigcal.projects.adventofcode"
version = "2024.0-SNAPSHOT"

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