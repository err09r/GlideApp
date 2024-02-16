plugins {
    id("com.apsl.glideapp.core-android")
    id("com.apsl.glideapp.hilt")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.security.crypto.ktx)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.security.crypto.datastore)
}
