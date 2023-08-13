plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.dagger.hilt.android.gradlePlugin)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
