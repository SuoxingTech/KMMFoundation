import org.gradle.api.publish.maven.MavenPublication

plugins {
    kotlin("multiplatform") version "2.2.21-1.0.0" apply false
}

subprojects {
    group = "dev.suoxing.kmm"
    plugins.withId("maven-publish") {
        extensions.configure<PublishingExtension> {
            repositories {
                maven {
                    name = "LocalVerification"
                    url = rootProject.layout.buildDirectory.dir("maven-repository").get().asFile.toURI()
                }
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/SuoxingTech/KMMFoundation")
                    credentials(PasswordCredentials::class)
                }
            }
            publications.withType<MavenPublication>().configureEach {
                pom {
                    name.set(project.name)
                    description.set("KMMFoundation foundations for CPF Harmony")
                    url.set("https://github.com/SuoxingTech/KMMFoundation")
                    scm { url.set("https://github.com/SuoxingTech/KMMFoundation") }
                    licenses {
                        license {
                            name.set("GNU General Public License v3.0")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                        }
                    }
                    developers {
                        developer { id.set("suoxingtech"); name.set("Suoxing Tech") }
                    }
                }
            }
        }
    }
}
