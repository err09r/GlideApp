import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.apsl.glideapp.android-app")
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.gms.googleServices)
    alias(libs.plugins.google.gradleSecrets)
    alias(libs.plugins.gradleVersions)
    alias(libs.plugins.kotlin.kapt)
}

android {
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:rides"))
    implementation(project(":feature:wallet"))

    implementation(project(":core:di"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.startup.runtime)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.toolingPreview)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)

    implementation(libs.firebase.analytics.ktx)
    implementation(libs.timber)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        arrayOf("alpha", "beta", "rc", "dev").any {
            candidate.version.contains(it, ignoreCase = true)
        }
    }
    checkForGradleUpdate = true
    outputDir = "${rootProject.buildDir}/dependencyUpdates"
    outputFormatter = Config.DependencyUpdates.outputFormatter
    reportfileName = Config.DependencyUpdates.reportfileName
}
