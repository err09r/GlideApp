plugins {
    id("com.apsl.glideapp.core")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.kapt)
}

android {
    buildTypes {
        configureEach {
            buildConfigField(
                "String",
                "GLIDE_API_BASE_URL_HTTP",
                "\"http://api-glide.herokuapp.com/\""
            )
            buildConfigField("String", "GLIDE_API_BASE_URL_WS", "\"ws://api-glide.herokuapp.com/\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:domain"))
    implementation(project(":core:location"))
    implementation(project(":core:network"))
    implementation(project(":core:util"))

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.paging.common)

    implementation(libs.bundles.ktor.client)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp3.loggingInterceptor)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.kotlinxSerializationConverter)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
}
