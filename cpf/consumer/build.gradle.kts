plugins {
    kotlin("multiplatform") version "2.2.21-1.0.0"
}
kotlin {
    ohosArm64 {
        binaries.sharedLib { baseName = "foundation_consumer" }
    }
    sourceSets {
        commonMain.dependencies {
            implementation("dev.suoxing.kmm:kmm-arch-cpf:1.7.0-cpf.1")
            implementation("dev.suoxing.kmm:kmm-kv-cpf:1.6.0-cpf.1")
            implementation("dev.suoxing.kmm:kmm-analytics-cpf:2.0.0-cpf.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2-1.0.0")
        }
    }
}
