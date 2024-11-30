plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "be.kdg.programming3"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.postgresql:postgresql")
    implementation("org.webjars:bootstrap:5.3.2")
    implementation("org.webjars:webjars-locator-core:0.59")
    //jackson library for converting the object to JSON
    implementation("com.fasterxml.jackson.core:jackson-databind")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
