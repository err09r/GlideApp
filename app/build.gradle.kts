plugins {
    id("com.apsl.glideapp.android-application")
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.gms.googleServices)
    alias(libs.plugins.google.gradleSecrets)
}

dependencies {
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:rides"))
    implementation(project(":feature:wallet"))

    implementation(project(":core:data"))
    implementation(project(":core:datastore"))
    implementation(project(":core:domain"))
    implementation(project(":core:location"))
    implementation(project(":core:navigation"))
    implementation(project(":core:network"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.startup.runtime)

    implementation(libs.firebase.analytics.ktx)
    implementation(libs.glideapp.common.dto)
    implementation(libs.timber)
}
