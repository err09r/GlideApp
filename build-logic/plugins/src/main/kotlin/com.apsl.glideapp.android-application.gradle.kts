import com.android.build.gradle.BaseExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("com.apsl.glideapp.hilt")
    kotlin("android")
}

val keystorePropertiesFile: File = rootProject.file("keystore.properties")
val keystoreProperties: Properties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

configure<BaseExtension> {
    commonAndroidConfiguration(project)
    commonComposeConfiguration(project)

    namespace = "${Config.namespace}.app"

    defaultConfig {
        applicationId = Config.applicationId
    }

    buildFeatures.buildConfig = true

    signingConfigs {
        maybeCreate("release").run {
            storeFile = file(keystoreProperties.getProperty("KEY_STORE_PATH"))
            storePassword = keystoreProperties.getProperty("KEY_STORE_PASSWORD")
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        staging {
            applicationIdSuffix = ".staging"
            isShrinkResources = true
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isShrinkResources = true
        }
    }
}
