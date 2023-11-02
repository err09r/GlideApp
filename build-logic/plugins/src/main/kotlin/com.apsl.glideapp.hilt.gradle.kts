import gradle.kotlin.dsl.accessors._7358b228c777673f552d03f9fcb098db.implementation

plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}
