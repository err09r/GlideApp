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
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlin.time.ExperimentalTime",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
                "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.paging.ExperimentalPagingApi",
                "-opt-in=com.google.maps.android.compose.MapsComposeExperimentalApi",
                "-opt-in=com.google.accompanist.pager.ExperimentalPagerApi",
                "-opt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi"
            ).run {
                when {
                    // Use `-Pcom.apsl.glideapp.enableComposeCompilerReports=true` to enable
                    findProperty("${Config.namespace}.enableComposeCompilerReports") == "true" -> {
                        this + listOf(
                            "-P=plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$buildDir/composeMetrics",
                            "-P=plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$buildDir/composeMetrics"
                        )
                    }

                    else -> this
                }
            }
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
