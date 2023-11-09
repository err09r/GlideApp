plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    "implementation"(libs.dagger.hilt.android) // Workaround to remove explicit 'implementation' import
    ksp(libs.dagger.hilt.compiler)
}
