@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.BaseExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun BaseExtension.commonAndroidConfiguration(target: Project) {
    configureDefaultConfig()
    configureBuildTypes()
    configureJavaCompileOptions()
    target.configureKotlinCompileOptions()
}

private fun BaseExtension.configureDefaultConfig() {
    namespace = Config.namespace
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner

        resourceConfigurations += Config.resourceConfigurations
    }
}

private fun BaseExtension.configureBuildTypes() {
    buildTypes {
        debug {
            isDebuggable = true
            manifestPlaceholders["performanceLogcatEnabled"] = true
        }
        staging {
            isDebuggable = false
            isMinifyEnabled = true
            manifestPlaceholders["performanceLogcatEnabled"] = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            manifestPlaceholders["performanceLogcatEnabled"] = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

private fun BaseExtension.configureJavaCompileOptions() {
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
}

private fun Project.configureKotlinCompileOptions() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            defaultKotlinConfiguration()
            freeCompilerArgs += listOf("-opt-in=androidx.paging.ExperimentalPagingApi")
        }
    }
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.debug(action: BuildTypeT.() -> Unit) {
    maybeCreate("debug").action()
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.staging(action: BuildTypeT.() -> Unit) {
    maybeCreate("staging").action()
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.release(action: BuildTypeT.() -> Unit) {
    maybeCreate("release").action()
}
