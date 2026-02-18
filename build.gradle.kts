plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.cocoapods) apply false
    alias(libs.plugins.android.library) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
