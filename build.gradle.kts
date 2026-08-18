plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
