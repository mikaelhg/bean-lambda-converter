plugins {
    `java`
    `maven-publish`
}

group = "io.mikael.convert"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(15))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.mockito:mockito-core:3.6.28")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
