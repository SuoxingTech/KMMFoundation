# KMMFoundation
A series of KMM(Kotlin Multiplatform Mobile) foundation libraries.

## Introduction

Official release of KMM libraries provided by SuoxingTech. Including:

- `kmm-arch` which provides fundamental MVVM Architecture Components (i.e. `ViewModel`).
- `kmm-kv` which provides Key-value storage solution. Jetpack `DataStore` for Android and `NSUserDefaults` for iOS.
- `kmm-database` which provides wrapped `Realm`'s Kotlin SDK.
- `kmm-analytics` which provides wrapped `FirebaseAnalytics` & `FirebaseCrashlytics`.

For more information about released packages you can visit Packages under our organization space.

## Latest version

| Library | Dependency | Version |
| :--: | :--: | :--: |
|`kmm_arch`| `dev.suoxing.kmm:kmm-arch` | ![github](https://img.shields.io/badge/github-v1.6.0-blue) |
|`kmm_kv`| `dev.suoxing.kmm:kmm-kv` | ![github](https://img.shields.io/badge/github-v1.3.0-blue) |
|`kmm_database`| `dev.suoxing.kmm:kmm-database` | ![github](https://img.shields.io/badge/github-v1.6.0-blue) |
|`kmm_analytics`| `dev.suoxing.kmm:kmm-analytics` | ![github](https://img.shields.io/badge/github-v1.4.2-blue) |

## Using GitHub Registry

Artifacts are currently published to GitHubPackages, which requires additional config on `dependencyResolutionManagement` block:

``` kotlin
dependencyResolutionManagement {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SuoxingTech/KMMFoundation")

            val prop = java.util.Properties().apply {
                load(java.io.FileInputStream(File(rootDir, "local.properties")))
            }
            val githubUser: String? = prop.getProperty("github.user")
            val githubToken: String? = prop.getProperty("github.token")

            credentials {
                username = githubUser
                password = githubToken
            }
        }
    }
}
```

## Add Dependency

``` kotlin
sourceSets {
    val commonMain by getting {
        dependencies {
            api("dev.suoxing.kmm:kmm-arch:$kmm_arch_ver")
            api("dev.suoxing.kmm:kmm-kv:$kmm_kv_ver")
            api("dev.suoxing.kmm:kmm-database:$kmm_database_ver")
        }
    }
}
```

> `kmm_analytics` may have issue on iOS builds. you can use only android artifact by add to android dependency like: `implementation("dev.suoxing.kmm:kmm_analytics-android:$kmm_analytics_ver")`
