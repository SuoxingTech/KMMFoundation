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

## Start using `kmm-arch` ViewModel

`dev.suoxing.kmm_arch.viewmodel.ViewModel` aims to make ViewModel cross-platform. So that most bussiness logic code could be placed in `shared` module.

### Implementing your ViewModel in `shared` module

It's simple to implement your own ViewModel class, just subclassing `dev.suoxing.kmm_arch.viewmodel.ViewModel` and define UiState class (must be `data class`) like following code:
``` kotlin
class HomeViewModel : ViewModel<HomeUiState>() {}
```

In addition, you might need `koin` to deal with dependency injection, in that case you need to wrap another `BaseViewModel` yourself:
``` kotlin
import dev.suoxing.kmm_arch.viewmodel.ViewModel
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<T: Any>() : ViewModel<T>(), KoinComponent
```

### Android Compose with LifecycleExt

``` kotlin
import androidx.lifecycle.compose.collectAsStateWithLifecycle

fun HomeScene(
    ...
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    ...
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
}
```

### iOS with SwiftUI @StateObject

For iOS you need to make a bridge helper, here is the sample code we are using internally:

``` swift
import Foundation
import shared
import SwiftUI

///
/// Wrap KMM ViewModel to `ObservableObject` with a published `uiState`.
///
@MainActor
class ObservableViewModel<UiState: AnyObject, VM: BaseViewModel<UiState>> : ObservableObject{
    
    ///
    /// `UiState` type can be inferred from `vm` instance passed to wrapper.
    ///
    @Published var uiState: UiState
    
    ///
    /// Real KMM ViewModel reference.
    ///
    /// Named as `actor` in order to inform developer to invoke this only for handling user actions.
    ///
    /// Little bit ugly, but I think it's okay. 😅
    ///
    let actor: VM
    
    init (_ vm: VM) {
        // peek latest value to guarantee that `uiState` is always non-null.
        self.uiState = vm.peek()
        self.actor = vm
        vm.collect { value in
            // update `uiState` everytime `uiStateFlow` emits new value.
            self.uiState = value
        }
    }
    
    ///
    /// - It is recommended to call it in [onAppear], which will check whether [viewModelScope] is active (because it may have been cancelled).
    /// If it is not active, a new [viewModelScope] can be created in time.
    /// - In fact, it is a manual implementation of life cycle management, which is equivalent to starting the viewModel when [onAppear] and pausing it when [onDisapper].
    /// (because it just cancels the viewModelScope), deinit is called by the system
    ///
    func activate() {
        // debugPrint(self.actor.description, ":vm:activate")
        self.actor.onViewAppear(onNewScope: { // onNewScope is called when the ViewModel creates a new [viewModelScope]
            // Because the viewModelScope was canceled, uiStateFlow needs to be collected again. Otherwise, it will not respond to the new state.
            self.actor.collect { value in
                // update `uiState` everytime `uiStateFlow` emits new value.
                self.uiState = value
            }
        })
    }
    
    ///
    /// - It is recommended to call it in onDisappear, which will cancel [viewModelScope]
    ///
    func clear() {
        // manually cancel coroutine scope, since `deinit` may never be called.
        // debugPrint(self.actor.description, ":vm:clear")
        self.actor.onCleared()
    }
    
    deinit {
        // cancel coroutine scope
        debugPrint(self.actor.description, ":vm:deinit")
        self.actor.onCleared()
    }
}
```

Then use it as any other @StateObject:

``` swift
struct MainScene: View {
    @StateObject private var viewModel = ObservableViewModel(HomeViewModel())

    var body: some View {
        MyView()
            .onAppear {
                viewModel.activate()
                viewModel.actor.start() // custom function initializing scene data
            }
            .onDisappear {
                viewModel.clear()
            }
    }
}
```
