plugins {
    id("java")
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")

}

group = "ru.otus.hw"
version = "1.0-SNAPSHOT"
