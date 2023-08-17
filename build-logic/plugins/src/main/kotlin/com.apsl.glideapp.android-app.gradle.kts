import com.android.build.gradle.BaseExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

val keystorePropertiesFile: File = rootProject.file("keystore.properties")
val keystoreProperties: Properties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

configure<BaseExtension> {
    commonAndroidConfiguration(project)

    defaultConfig {
        applicationId = Config.applicationId
    }

    signingConfigs {
        create("staging") {
            storeFile = file(keystoreProperties.getProperty("KEY_STORE_PATH"))
            storePassword = keystoreProperties.getProperty("KEY_STORE_PASSWORD")
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
        }
        create("release") {
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
            signingConfig = signingConfigs.getByName("staging")
        }
        release {
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
