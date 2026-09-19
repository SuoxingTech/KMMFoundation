plugins {
    kotlin("multiplatform")
    `maven-publish`
}
version = "1.6.0-cpf.1"
kotlin {
    ohosArm64()
    sourceSets {
        commonMain {
            kotlin.srcDir("../../kmm_kv/src/commonMain/kotlin")
            dependencies {
                implementation("androidx.datastore:datastore-preferences-core:1.3.0-alpha05-0.3.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2-1.0.0")
            }
        }
        val ohosMain by creating { dependsOn(commonMain.get()) }
        getByName("ohosArm64Main").dependsOn(ohosMain)

    }
}
