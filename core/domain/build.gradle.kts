plugins {
    id("com.apsl.glideapp.kotlin-library")
}

dependencies {
    implementation(libs.androidx.paging.common)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)

    api(libs.glideapp.common.models)
    api(libs.glideapp.common.util)
}
