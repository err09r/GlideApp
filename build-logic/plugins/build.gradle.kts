plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.dagger.hilt.android.gradlePlugin)
    implementation(libs.google.ksp.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
