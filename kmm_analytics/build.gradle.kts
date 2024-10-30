import ext.configureMavenPublish
import ext.firebaseAndroid
import ext.firebaseApple
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

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "kmm_analytics"
            isStatic = true
            embedBitcodeMode = BitcodeEmbeddingMode.DISABLE
        }
        pod("FirebaseAnalytics") {
            version = firebaseApple
            extraOpts = listOf("-compiler-option", "-fmodules")
        }
        pod("FirebaseCrashlytics") {
            version = firebaseApple
            extraOpts = listOf("-compiler-option", "-fmodules")
        }
    }
    
    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(project.dependencies.platform("com.google.firebase:firebase-bom:$firebaseAndroid"))
                implementation("com.google.firebase:firebase-analytics-ktx")
                implementation("com.google.firebase:firebase-crashlytics-ktx")
            }
        }
    }
}

android {
    namespace = "dev.suoxing.kmm_analytics"
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
        artifactId = "kmm-analytics",
        version = "1.4.3",
        descriptions = "KMM analytics util using Firebase.",
        publishingExtension = this
    )
}