plugins {
    id("com.apsl.glideapp.core-android")
    id("com.apsl.glideapp.hilt")
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:domain"))
    implementation(project(":core:util"))

    implementation(libs.kotlinx.coroutines.core)
}
