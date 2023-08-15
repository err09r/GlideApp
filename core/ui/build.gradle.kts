plugins {
    id("com.apsl.glideapp.core")
}

android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.toolingPreview)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.glideapp.common.util)

    debugImplementation(libs.androidx.compose.ui.tooling)
}
