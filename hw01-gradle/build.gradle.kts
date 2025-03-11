plugins {
    id("java")
    id("com.github.johnrengelman.shadow")
}

group = "ru.otus.hw"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks.shadowJar {
    archiveBaseName.set("hw-01-malafeev")
    archiveVersion.set("0.1")
    archiveClassifier.set("")
    manifest {
        attributes(mapOf("Main-Class" to "ru.otus.hw.HelloOtus"))
    }
}



tasks.test {
    useJUnitPlatform()
}