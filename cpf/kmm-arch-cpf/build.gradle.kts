plugins {
    kotlin("multiplatform")
    `maven-publish`
}
version = "1.7.0-cpf.1"
kotlin {
    ohosArm64()
    ohosX64()
    sourceSets {
        commonMain {
            kotlin.srcDir("../../kmm_arch/src/commonMain/kotlin")
            dependencies {
                api("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel:2.9.4-1.0.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2-1.0.0")
            }
        }
        val ohosMain by creating { dependsOn(commonMain.get()) }
        getByName("ohosArm64Main").dependsOn(ohosMain)
        getByName("ohosX64Main").dependsOn(ohosMain)
    }
}
