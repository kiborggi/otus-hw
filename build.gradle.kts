plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ru.otus.hw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks {
    build{
        dependsOn(subprojects.map { it.tasks.named("shadowJar") })
    }
}

tasks.test {
    useJUnitPlatform()
}

subprojects {
    apply(plugin = "java") // Подключаем плагин Java во всех дочерних модулях

    repositories {
        mavenCentral()
    }

    dependencies {
        // Используем версию Guava из gradle.properties
        implementation("com.google.guava:guava:${project.findProperty("guava")}")
    }
}