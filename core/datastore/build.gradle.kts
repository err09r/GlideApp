plugins {
    id("com.apsl.glideapp.core-android")
    id("com.apsl.glideapp.hilt")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.datastore)
    implementation(libs.glideapp.common.models)
    implementation(libs.androidx.security.crypto.ktx)
    implementation(libs.security.crypto.datastore)
    implementation(libs.kotlinx.serialization.protobuf)
}
