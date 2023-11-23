
plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    api("org.apache.commons:commons-math3:3.6.1")

    implementation("com.google.guava:guava:32.1.1-jre")
    implementation("org.springframework:spring-webflux:6.0.11")

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

version = "0.1.0"

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.named<Jar>("jar"){
    archiveBaseName.set("mockingbird")
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}
