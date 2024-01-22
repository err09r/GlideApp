plugins {
    id("com.apsl.glideapp.core-android")
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.glideapp.common.models)
    implementation(libs.google.maps.utils.ktx)
}
