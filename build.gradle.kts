import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
}

allprojects {
    apply(plugin = "java")

    group = "com.wizardlybump17.physics"
    version = "0.1.0"

    repositories {
        mavenCentral()
    }

    val junit = "5.11.0"

    dependencies {
        testImplementation(platform("org.junit:junit-bom:${junit}"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks {
        test {
            useJUnitPlatform()
            testLogging {
                events(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.STARTED, TestLogEvent.STANDARD_ERROR, TestLogEvent.STANDARD_OUT)
            }
        }
    }
}
