rootProject.name = "java-fat-jar-deployment"

pluginManagement {
    plugins {
        id("com.github.hauner.jarTest").version(settings.extra["plugin.jarTest.version"]!!.toString()).apply(false)
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}
