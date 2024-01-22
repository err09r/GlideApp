plugins {
    id("com.apsl.glideapp.core-android")
    id("com.apsl.glideapp.hilt")
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:util:android"))

    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.glideapp.common.dto)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core.jvm)
}
