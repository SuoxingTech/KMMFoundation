pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "KMMFoundation"
include(":kmm_arch")
include(":kmm_analytics")
include(":kmm_kv")
include(":kmm_database")

includeBuild("convention-plugins")