plugins {
    id("com.apsl.glideapp.feature")
}

dependencies {
    implementation(project(":core:util:maps"))
    implementation(libs.androidx.paging.compose)
    implementation(libs.google.gms.playServices.maps)
    implementation(libs.google.maps.compose)
    implementation(libs.kotlinx.datetime)
}
