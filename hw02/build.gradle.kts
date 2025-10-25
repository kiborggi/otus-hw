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
    val junitJupiter: String by project


    repositories {
        mavenLocal()
        mavenCentral()
    }

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            dependency("com.google.guava:guava:$guava")
            // Добавьте версии для JUnit Jupiter и AssertJ
            dependency("org.junit.jupiter:junit-jupiter:$junitJupiter")
            dependency("org.assertj:assertj-core:3.24.2") // или актуальная версия
        }
    }
}

// Добавьте зависимости в основной блок dependencies
dependencies {
    implementation("com.google.guava:guava")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
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
    apply(plugin = "com.github.johnrengelman.shadow") // Применяем shadow плагин к сабпроектам

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.assertj:assertj-core")
    }
}

tasks.shadowJar {
    archiveBaseName.set("hw-02-malafeev")
    archiveVersion.set("0.1")
    archiveClassifier.set("")
    manifest {
        attributes(mapOf("Main-Class" to "ru.otus.hw.HelloOtus"))
    }
}

tasks.test {
    useJUnitPlatform()
}