plugins {
    kotlin("multiplatform")
    `maven-publish`
}
version = "2.0.0-cpf.1"
kotlin {
    ohosArm64()
    ohosX64()
    sourceSets {
        commonMain {
            kotlin.srcDir("../../kmm_analytics/src/commonMain/kotlin")
            dependencies {

            }
        }
        val ohosMain by creating { dependsOn(commonMain.get()) }
        getByName("ohosArm64Main").dependsOn(ohosMain)
        getByName("ohosX64Main").dependsOn(ohosMain)
    }
}
