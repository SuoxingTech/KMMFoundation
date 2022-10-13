pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "KMMFoundation"
include(":shared")
include(":kmm_arch")
include(":kmm_analytics")
includeBuild("convention-plugins")