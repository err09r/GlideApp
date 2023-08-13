plugins {
    id("com.apsl.glideapp.core")
    alias(libs.plugins.google.ksp)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
}

dependencies {
    implementation(libs.glideapp.common.models)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
}
