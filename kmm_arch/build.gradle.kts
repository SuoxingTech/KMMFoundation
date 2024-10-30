import ext.configureMavenPublish
import ext.coroutine
import ext.lifecycle
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("convention.publications")
}

version = "1.0"

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget {
        publishLibraryVariants("release")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js(IR)

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "kmm_arch"
            isStatic = true
            embedBitcodeMode = BitcodeEmbeddingMode.DISABLE
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine")
                api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
            }
        }
    }
}

android {
    namespace = "dev.suoxing.kmm_arch"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
    }
    kotlin {
        jvmToolchain(21)
    }
}

publishing {
    configureMavenPublish(
        artifactId = "kmm-arch",
        version = "1.7.0",
        descriptions = "KMM architecture foundations.",
        publishingExtension = this
    )
}