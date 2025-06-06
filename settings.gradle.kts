rootProject.name = "java-fat-jar-deployment"

pluginManagement {
    plugins {
        id("com.github.hauner.jarTest").version(settings.extra["plugin.jarTest.version"]!!.toString()).apply(false)
        id("org.springframework.boot") version settings.extra["plugin.spring-boot.version"]!!.toString() apply false
        id("io.spring.dependency-management") version settings.extra["plugin.spring-dependency-management.version"]!!.toString() apply false
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}
