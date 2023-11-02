plugins {
    id("com.apsl.glideapp.feature")
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    implementation(project(":core:util:maps"))

    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.compose.materialIconsExtended)
    implementation(libs.glideapp.common.util)
    implementation(libs.google.gms.playServices.location)
    implementation(libs.google.gms.playServices.maps)
    implementation(libs.google.maps.compose)
    implementation(libs.google.maps.compose.utils)
    implementation(libs.google.maps.compose.widgets)
    implementation(libs.kotlinx.datetime)
}
