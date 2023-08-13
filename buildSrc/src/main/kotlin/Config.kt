import org.gradle.api.JavaVersion

object Config {
    const val namespace = "com.apsl.glideapp"
    const val applicationId = "com.apsl.glideapp"

    const val minSdk = 26
    const val compileSdk = 34
    const val targetSdk = 33

    const val versionName = "0.0.1"
    const val versionCode = 1

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"

    val resourceConfigurations = arrayOf("en")

    object DependencyUpdates {
        const val outputFormatter = "html"
        const val reportfileName = "report"
    }
}
