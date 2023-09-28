plugins {
    id("com.apsl.glideapp.core-android")
    id("com.apsl.glideapp.hilt")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.security.crypto.ktx)
    implementation(libs.glideapp.common.models)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.security.crypto.datastore)
}
