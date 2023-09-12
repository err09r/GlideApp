import com.android.build.gradle.BaseExtension

plugins {
    id("com.android.test")
    kotlin("android")
}

configure<BaseExtension> {
    commonAndroidConfiguration(project)
}
