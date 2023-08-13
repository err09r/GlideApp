plugins {
    id("com.apsl.glideapp.core")
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:util"))

    implementation(libs.dagger.hilt.android)
    implementation(libs.google.gms.playServices.location)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)
}
