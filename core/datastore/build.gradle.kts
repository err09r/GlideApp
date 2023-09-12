plugins {
    id("com.apsl.glideapp.core")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.datastore)
    implementation(libs.glideapp.common.models)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.serialization.protobuf)
}
