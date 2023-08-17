plugins {
    id("com.apsl.glideapp.core")
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.kotlinx.coroutines.core)
}
