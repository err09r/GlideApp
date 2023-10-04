plugins {
    id("com.apsl.glideapp.core-android")
    id("com.apsl.glideapp.core-compose")
}

dependencies {
    implementation(project(":core:util:android"))

    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.glideapp.common.util)
}
