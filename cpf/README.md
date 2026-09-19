# KMMFoundation CPF / Harmony

This directory is a standalone Gradle project using CPF Kotlin `2.2.21-1.0.0`, Gradle `8.9`, and JDK 21.
The repository's root project continues to use official Kotlin for Android/iOS builds. Both projects share `../kmm_*/src/commonMain/kotlin`, with platform implementations maintained separately.

## Artifacts

The group is `dev.suoxing.kmm`. CPF modules have independent release versions.

| artifactId | Current version | Targets |
|---|---|---|
| kmm-arch-cpf | 1.7.0-cpf.1 | ohosArm64, ohosX64 |
| kmm-kv-cpf | 1.6.0-cpf.1 | ohosArm64 |
| kmm-analytics-cpf | 2.0.0-cpf.1 | ohosArm64, ohosX64 |

Gradle automatically publishes the root modules and the `-ohosarm64` / `-ohosx64` platform modules, maintaining references in POM files and Gradle Module Metadata. Consumers can use the root coordinates listed above.
Android/iOS use the original artifacts; Harmony uses `*-cpf`. Including both implementations in the same compilation produces duplicate Kotlin classes.

## Building and Publishing Locally

Run the following commands from `cpf/`. `gradlew-cpf` checks `CPF_JAVA_HOME` first, then `JAVA_HOME`, and finally looks for a JDK 21 registered with macOS.

```bash
export CPF_JAVA_HOME=/absolute/path/to/jdk21/Contents/Home
./gradlew-cpf publishAllPublicationsToLocalVerificationRepository
./gradlew-cpf -p consumer compileKotlinOhosArm64
```

The temporary Maven repository is located at `cpf/build/maven-repository`. `consumer/` is a standalone consumer example that resolves all three artifacts through Maven coordinates and uses the public ViewModel, AppKV, and analytics APIs.

Linking the shared library also requires the Harmony Native SDK from DevEco Studio. Configure its location in `consumer/local.properties`:

```properties
local.deveco.path=/Applications/DevEco-Studio.app
```

```bash
./gradlew-cpf -p consumer linkDebugSharedOhosArm64
```

Each module also supports the standard `publishToMavenLocal` task.

## Publishing to GitHub Packages

The three versions listed above and five platform artifacts were published on 2026-09-17. Remote read verification passed for 8 POM files, 8 module metadata files, and the checksums of 16 artifact files. Increment the CPF version numbers for subsequent changes.

The publishing destination remains `https://maven.pkg.github.com/SuoxingTech/KMMFoundation`.
Credentials use Gradle's named repository properties and are read when remote publishing tasks run. Set `GitHubPackagesUsername` and `GitHubPackagesPassword` in the user-level `~/.gradle/gradle.properties`, or inject them through CI:

```bash
export ORG_GRADLE_PROJECT_GitHubPackagesUsername="$GPR_USER"
export ORG_GRADLE_PROJECT_GitHubPackagesPassword="$GPR_KEY"
./gradlew-cpf publishAllPublicationsToGitHubPackagesRepository
```

Update the version in each module's `build.gradle.kts` before publishing. Published versions should remain immutable. The root project's `github.properties` continues to serve the original publishing workflow; the CPF project uses the separate credential configuration described above.

Consumers must configure both GitHub Packages (with read credentials) and the CPF Maven repository:

```kotlin
maven("https://maven.eazytec-cloud.com/nexus/repository/maven-public/")
```

Declare the dependencies in the Harmony project:

```kotlin
commonMain.dependencies {
    implementation("dev.suoxing.kmm:kmm-arch-cpf:1.7.0-cpf.1")
    implementation("dev.suoxing.kmm:kmm-kv-cpf:1.6.0-cpf.1")
    implementation("dev.suoxing.kmm:kmm-analytics-cpf:2.0.0-cpf.1")
}
```

Projects using all three modules should currently target `ohosArm64`. Projects targeting x64 require KV's x64 dependencies and artifacts to be added first.

## Harmony Initialization

### arch

The Harmony `ViewModel` extends CPF's `androidx.lifecycle.ViewModel`, preserving the existing state flows and custom CoroutineScope API. Pages manage instances through ViewModelStore. Clearing the store triggers `onCleared()` and cancels the scope.

The host must call CPF's `kotlinx.coroutines.initMainHandler(env)` to initialize `Dispatchers.Main` before creating a ViewModel. `ioDispatcher` uses `Dispatchers.IO`, and `defaultDispatcher` uses `Dispatchers.Default`.

### kv

At startup, pass Harmony's `context.filesDir` to Kotlin and complete initialization sequentially before allowing application code to access it:

```kotlin
val preferences = AppKV()
preferences.init("$filesDir/app.preferences_pb")
// Preload within a coroutine.
preferences.preloadDataStore()
preferences.putString("name", "Travelog")
val name = preferences.getString("name", "")
```

- File paths must be absolute and end with `.preferences_pb`. The host should consistently pass the same canonical path, avoiding different symbolic links to the same file.
- Access to the same canonical path within a process shares one DataStore instance for the application's lifetime. Each AppKV instance is bound to one file.
- Reads and writes retain synchronous semantics: calls return after the operation completes, and errors propagate to the caller. Both DataStore and synchronous waiting use the IO dispatcher. Synchronous calls still occupy the calling thread, so application coroutines should schedule batch operations in the background.
- `clear(ignoreKeys)` removes all data except the specified retained keys in a single DataStore update; `delete` supports deletion by key name.
- CPF DataStore `1.3.0-alpha05-0.3.0` is currently published only for Harmony ARM64. Its transitive dependencies include the CPF `0.3.0` series. This project directly uses coroutines `1.10.2-1.0.0`, with the remaining dependencies resolved by Gradle.

### analytics

The module reuses `SXAnalytics`, `SXCrashlytics`, and the shared adapter interfaces. After initializing the SDKs, the host registers implementations by calling `SXAnalytics.register(...)` and `SXCrashlytics.register(...)`.

## Validation Scope

Validation results from 2026-09-17: all three root modules and five platform modules were published successfully, and all files referenced by the metadata were present. ARM64 and x64 KLIB compilation passed for arch/analytics, and ARM64 compilation passed for KV. ARM64 compilation and shared library linking also passed for the standalone Maven consumer project. CPF's generated C adapter code produced two return-value warnings; linking succeeded.

Local publishing and consumer validation cover Maven coordinates, metadata, KLIB compilation, and shared library linking. On-device persistence, recovery after restart, concurrent calls, and page destruction behavior require validation after integration with the Harmony host.
