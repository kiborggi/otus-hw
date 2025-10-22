plugins {
    idea
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.otus.hw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
allprojects {
    group = "ru.otus.hw"

    val guava: String by project


    repositories {
        mavenLocal()
        mavenCentral()
    }

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            dependency("com.google.guava:guava:$guava")
        }
    }

}

tasks {
    build {
        dependsOn(subprojects.map { it.tasks.named("shadowJar") })
    }
}

tasks.test {
    useJUnitPlatform()
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

}