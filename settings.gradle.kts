@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "GlideApp"

include(":app")

include(":feature:auth")
include(":feature:home")
include(":feature:rides")
include(":feature:wallet")

include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:di")
include(":core:domain")
include(":core:location")
include(":core:navigation")
include(":core:network")
include(":core:ui")
include(":core:util")
