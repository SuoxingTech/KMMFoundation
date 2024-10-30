import ext.configureMavenPublish
import ext.coroutine
import ext.realm
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("io.realm.kotlin")
    id("convention.publications")
}

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
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "kmm_database"
            isStatic = true
            embedBitcodeMode = BitcodeEmbeddingMode.DISABLE
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine")
                api("io.realm.kotlin:library-base:$realm")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "dev.suoxing.kmm_database"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    kotlin {
        jvmToolchain(21)
    }
}

publishing {
    configureMavenPublish(
        artifactId = "kmm-database",
        version = "1.7.0",
        descriptions = "KMM realm database.",
        publishingExtension = this
    )
}