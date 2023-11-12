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
        kotlinOptions.freeCompilerArgs += optInCompilerArgs() + composeCompilerArgs(project)
    }
}

private fun optInCompilerArgs(): List<String> {
    return listOf(
        "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
        "-opt-in=com.google.accompanist.permissions.ExperimentalPermissionsApi",
        "-opt-in=com.google.maps.android.compose.MapsComposeExperimentalApi"
    )
}

private fun composeCompilerArgs(target: Project): List<String> {
    val composePluginId = "androidx.compose.compiler.plugins.kotlin"
    val composeMetricsDir = target.layout.buildDirectory.dir("composeMetrics").get()
    val stabilityConfigurationPath = "${target.rootDir}/app/compose-stability-config.txt"

    val resultList = mutableListOf<String>()

    // Use `-PenableComposeCompilerReports=true` to enable
    if (target.findProperty("enableComposeCompilerReports") == "true") {
        resultList.add("-P=plugin:$composePluginId:reportsDestination=$composeMetricsDir")
        resultList.add("-P=plugin:$composePluginId:metricsDestination=$composeMetricsDir")
    }

    // Uncomment once https://issuetracker.google.com/issues/309765121 is fixed
//    resultList.add("-P=plugin:$composePluginId:stabilityConfigurationPath=$stabilityConfigurationPath")

    return resultList.toList()
}
