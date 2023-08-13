import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("jvm")
}

java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Config.jvmTarget
        freeCompilerArgs += listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlin.time.ExperimentalTime",
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}
