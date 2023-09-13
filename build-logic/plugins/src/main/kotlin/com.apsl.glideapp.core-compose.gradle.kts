import com.android.build.gradle.BaseExtension

plugins {
    id("com.apsl.glideapp.core-android")
}

configure<BaseExtension> {
    commonComposeConfiguration(project)
}
