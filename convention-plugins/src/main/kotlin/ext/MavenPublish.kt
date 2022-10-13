package ext

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Configure base maven publish options
 */
fun Project.configureMavenPublish(
    artifactId: String,
    version: String,
    descriptions: String,
    publishingExtension: PublishingExtension,
) {
    val prop = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "github.properties")))
    }
    val user: String? = prop.getProperty("gpr.user")
    val key: String? = prop.getProperty("gpr.key")

    publishingExtension.apply {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/SuoxingTech/KMMFoundation")
                credentials {
                    username = user ?: System.getenv("GPR_USER")
                    password = key ?: System.getenv("GPR_KEY")
                }
            }
        }
        publications {
            create<MavenPublication>("gpr") {

                this.groupId = "dev.suoxing.kmm"
                this.artifactId = artifactId
                this.version = version

                pom {
                    packaging = "jar"
                    name.set(artifactId)
                    description.set(descriptions)
                    url.set("https://github.com/SuoxingTech/KMMFoundation")
                    scm {
                        url.set("https://github.com/SuoxingTech/KMMFoundation")
                    }
                    issueManagement {
                        url.set("https://github.com/SuoxingTech/KMMFoundation/issues")
                    }
                    licenses {
                        license {
                            name.set("GNU General Public License v3.0")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                        }
                    }
                    developers {
                        developer {
                            id.set("suoxingtech")
                            name.set("Suoxing Tech")
                        }
                    }
                }
            }
        }
    }
}