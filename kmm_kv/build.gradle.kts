import ext.configureMavenPublish
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("convention.publications")
}

val libraryVersion = "1.6.0"
version = libraryVersion

kotlin {
    android {
        namespace = "dev.suoxing.kmm_kv"
        compileSdk = 37
        minSdk = 26

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }

        withHostTest {}
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            api(libs.androidx.datastore.preferences)
        }
    }
}

publishing {
    configureMavenPublish(
        artifactId = "kmm-kv",
        version = libraryVersion,
        descriptions = "KMM key-value storage library.",
        publishingExtension = this
    )
}
