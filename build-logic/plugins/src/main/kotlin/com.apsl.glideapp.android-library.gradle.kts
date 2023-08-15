import com.android.build.gradle.BaseExtension

plugins {
    id("com.android.library")
    kotlin("android")
}

configure<BaseExtension> {
    commonAndroidConfiguration(project)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.timber)
}
