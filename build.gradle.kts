import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    id("maven-publish")
    id("com.palantir.git-version") version "3.1.0"
}

val gitVersion: groovy.lang.Closure<String> by extra

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.palantir.git-version")

    group = "com.wizardlybump17.physics"
    version = "${properties["version"]}-${gitVersion()}"

    repositories {
        mavenCentral()
    }

    val junit = "5.11.0"

    dependencies {
        testImplementation(platform("org.junit:junit-bom:${junit}"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(21)
        }

        test {
            useJUnitPlatform()
            testLogging {
                events(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.STARTED, TestLogEvent.STANDARD_ERROR, TestLogEvent.STANDARD_OUT)
            }
        }

        java {
            withSourcesJar()
        }
    }
}

subprojects {
    publishing {
        repositories {
            maven {
                url = uri("https://maven.pkg.github.com/WizardlyBump17/physics")
                credentials {
                    username = (project.findProperty("gpr.user") ?: System.getenv("USERNAME")) as String
                    password = (project.findProperty("gpr.key") ?: System.getenv("TOKEN")) as String
                }
            }
        }

        publications {
            create<MavenPublication>("github-packages") {
                from(components["java"])
            }
        }
    }
}
