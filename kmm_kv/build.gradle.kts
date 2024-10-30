import ext.configureMavenPublish
import ext.dataStore
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

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
    js("harmony", IR) {
        generateTypeScriptDefinitions()
        useEsModules()
        nodejs()
        binaries.executable()
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "kmm_kv"
            isStatic = true
            embedBitcodeMode = BitcodeEmbeddingMode.DISABLE
        }
    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.datastore:datastore-preferences:$dataStore")
            }
        }
        val harmonyMain by getting {
            dependencies {

            }
        }
    }
}

android {
    namespace = "dev.suoxing.kmm_kv"
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
        artifactId = "kmm-kv",
        version = "1.4.0",
        descriptions = "KMM key-value storage library.",
        publishingExtension = this
    )
}