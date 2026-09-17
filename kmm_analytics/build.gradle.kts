import ext.configureMavenPublish
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("convention.publications")
}

val libraryVersion = "2.0.0"
version = libraryVersion

kotlin {
    android {
        namespace = "dev.suoxing.kmm_analytics"
        compileSdk = 37
        minSdk = 26

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }

        withHostTest {}
    }
    iosArm64()
    iosSimulatorArm64()
}

publishing {
    configureMavenPublish(
        artifactId = "kmm-analytics",
        version = libraryVersion,
        descriptions = "Provider-independent analytics and crash reporting facade for KMM.",
        publishingExtension = this
    )
}
