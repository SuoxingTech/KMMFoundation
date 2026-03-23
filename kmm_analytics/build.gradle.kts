import ext.configureMavenPublish

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.android.library)
    id("convention.publications")
}

val libraryVersion = "1.5.0"
version = libraryVersion

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
        version = project.version.toString()
        ios.deploymentTarget = "15.0"
        framework {
            baseName = "kmm_analytics"
            isStatic = true
            transitiveExport = true
        }
        pod("FirebaseCore") {
            version = libs.versions.firebaseApple.get()
        }
        pod("FirebaseAnalytics") {
            version = libs.versions.firebaseApple.get()
        }
        pod("FirebaseCrashlytics") {
            version = libs.versions.firebaseApple.get()
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.crashlytics)
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
        version = libraryVersion,
        descriptions = "KMM analytics util using Firebase.",
        publishingExtension = this
    )
}
