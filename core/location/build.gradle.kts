plugins {
    id("com.apsl.glideapp.core-android")
    id("com.apsl.glideapp.hilt")
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:domain"))
    implementation(project(":core:util:android"))
    implementation(project(":core:util:maps"))

    implementation(libs.kotlinx.coroutines.core)
}
