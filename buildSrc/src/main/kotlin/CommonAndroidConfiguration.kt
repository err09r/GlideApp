@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.BaseExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun BaseExtension.commonAndroidConfiguration(target: Project) {
    val kotlinCompilerExtensionVersion = target.libs.versions.androidx.compose.compiler.get()
    configureDefaultConfig(kotlinCompilerExtensionVersion)
    configureBuildTypes()
    configureJavaCompileOptions()
    target.configureKotlinCompileOptions()
}

private fun BaseExtension.configureDefaultConfig(kotlinCompilerExtensionVersion: String) {
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

    composeOptions {
        this.kotlinCompilerExtensionVersion = kotlinCompilerExtensionVersion
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

private fun BaseExtension.configureBuildTypes() {
    buildTypes {
        debug {
            isDebuggable = true
        }
        staging {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

private fun BaseExtension.configureJavaCompileOptions() {
    compileOptions.sourceCompatibility = Config.javaVersion
    compileOptions.targetCompatibility = Config.javaVersion
}

private fun Project.configureKotlinCompileOptions() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = Config.jvmTarget
            freeCompilerArgs += listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xopt-in=kotlin.time.ExperimentalTime",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
                "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-Xopt-in=androidx.paging.ExperimentalPagingApi",
                "-Xopt-in=com.google.maps.android.compose.MapsComposeExperimentalApi",
                "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi",
                "-Xopt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi"
            )
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
