plugins {
    id("com.github.johnrengelman.shadow")
}


dependencies {
    implementation("com.google.guava:guava")
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