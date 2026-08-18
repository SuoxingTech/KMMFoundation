import ext.configureMavenPublish
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("convention.publications")
}

val libraryVersion = "1.7.0"
version = libraryVersion

kotlin {
    android {
        namespace = "dev.suoxing.kmm_arch"
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
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            api(libs.androidx.lifecycle.viewmodel.ktx)
        }
    }
}

publishing {
    configureMavenPublish(
        artifactId = "kmm-arch",
        version = libraryVersion,
        descriptions = "KMM architecture foundations.",
        publishingExtension = this
    )
}
