import ext.configureMavenPublish

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.android.library)
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
        }
        pod("FirebaseAnalytics") {
            version = libs.versions.firebaseApple.get()
            extraOpts = listOf("-compiler-option", "-fmodules")
        }
        pod("FirebaseCrashlytics") {
            version = libs.versions.firebaseApple.get()
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
                implementation(project.dependencies.platform(libs.firebase.bom.android))
                implementation(libs.firebase.analytics.ktx)
                implementation(libs.firebase.crashlytics.ktx)
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
