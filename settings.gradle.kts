@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
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
include(":core:domain")
include(":core:location")
include(":core:model")
include(":core:network")
include(":core:ui")
include(":core:util:android")
include(":core:util:maps")
