import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.gradleVersions)
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        listOf("alpha", "beta", "rc", "dev").any {
            candidate.version.contains(it, ignoreCase = true)
        }
    }
    checkForGradleUpdate = true
    outputDir = "build/dependencyUpdates"
    outputFormatter = "html"
    reportfileName = "report"
}
