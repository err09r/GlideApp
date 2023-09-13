plugins {
    id("com.apsl.glideapp.feature")
}

dependencies {
    implementation(libs.accompanist.pager)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.materialIconsExtended)
    implementation(libs.kotlinx.datetime)
}
