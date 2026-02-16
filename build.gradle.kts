plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.cocoapods) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.realm.kotlin) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
