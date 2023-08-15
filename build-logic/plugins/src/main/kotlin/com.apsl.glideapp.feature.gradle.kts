plugins {
    id("com.apsl.glideapp.android-library")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "${Config.namespace}.feature.${project.name}"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))

    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.materialIconsExtended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.compose.ui.util)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.compose)

    implementation(libs.kotlinx.datetime)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigationCompose)

    debugImplementation(libs.androidx.compose.ui.tooling)
}
