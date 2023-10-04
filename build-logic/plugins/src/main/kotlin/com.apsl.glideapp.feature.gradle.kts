import com.android.build.gradle.BaseExtension

plugins {
    id("com.apsl.glideapp.android-library")
    id("com.apsl.glideapp.hilt")
}

configure<BaseExtension> {
    commonComposeConfiguration(project)
    namespace = "${Config.namespace}.feature.${project.name}"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:util:android"))

    implementation(libs.androidx.hilt.navigationCompose)
    implementation(libs.androidx.navigation.compose)
}
