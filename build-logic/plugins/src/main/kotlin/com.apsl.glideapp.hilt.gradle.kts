import gradle.kotlin.dsl.accessors._272b7390582f4cf8bb9f6f57d5913699.implementation

plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}
