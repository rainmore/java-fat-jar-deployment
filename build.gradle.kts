import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    application
    idea
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "au.com.rainmore"
version = "1.0.0"
description = "Java Fat Jar Deployment"

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

application {
    mainClass.set(listOf(project.group.toString(), "Application").joinToString("."))
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("org.springframework.boot:spring-boot-properties-migrator")

    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-logging")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.apache.commons:commons-lang3:${project.properties["commons-lang3.version"]!!}")
    implementation("org.slf4j:slf4j-api")
    implementation("org.slf4j:jcl-over-slf4j")
    implementation("org.slf4j:log4j-over-slf4j")
    implementation("ch.qos.logback:logback-classic")
    implementation("ch.qos.logback:logback-core")
    testImplementation(platform("org.junit:junit-bom:${project.properties["junit-jupiter.version"]!!.toString()}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core")
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = project.properties["gradle.version"] as String
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:unchecked")
}

tasks.withType<BootRun> {
    args("--spring.profiles.active=dev")
    jvmArgs = listOf(
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
    )
}


// use BootJar only
tasks.jar {
    enabled = false
}

tasks.run.configure {
    standardInput = System.`in`
}

tasks.withType<Test> {
    enabled = false
    outputs.upToDateWhen { false }
    useJUnitPlatform {
        testLogging {
            events(
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.FAILED,
                TestLogEvent.STANDARD_ERROR)
        }
    }
}
