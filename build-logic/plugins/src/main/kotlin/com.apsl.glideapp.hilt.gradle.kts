import gradle.kotlin.dsl.accessors._d3eb573f25c1970d5907a3076fb1b5c1.implementation

plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}
