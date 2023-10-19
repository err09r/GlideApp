@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun BaseExtension.commonComposeConfiguration(target: Project) {
    configureCompose(target)
    target.configureDependencies()
    target.configureKotlinCompileOptions()
}

private fun BaseExtension.configureCompose(target: Project) {
    composeOptions {
        kotlinCompilerExtensionVersion = target.libs.versions.androidx.compose.compiler.get()
    }

    buildFeatures.compose = true
}

private fun Project.configureDependencies() {
    dependencies {
        add("implementation", libs.androidx.compose.material3)
        add("implementation", libs.androidx.compose.ui)
        add("implementation", libs.androidx.compose.ui.toolingPreview)
        add("implementation", libs.androidx.lifecycle.compose)
        add("debugImplementation", libs.androidx.compose.ui.tooling)
    }
}

private fun Project.configureKotlinCompileOptions() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
            "-opt-in=com.google.accompanist.pager.ExperimentalPagerApi",
            "-opt-in=com.google.accompanist.permissions.ExperimentalPermissionsApi"
        ).run {
            when {
                // Use `-PenableComposeCompilerReports=true` to enable
                findProperty("enableComposeCompilerReports") == "true" -> {
                    this + listOf(
                        "-P=plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${
                            layout.buildDirectory.dir("composeMetrics").get()
                        }",
                        "-P=plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${
                            layout.buildDirectory.dir("composeMetrics").get()
                        }"
                    )
                }

                else -> this
            }
        }
    }
}
