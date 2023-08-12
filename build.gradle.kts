plugins {
    java
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}
group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    boot("spring-boot-starter")
    boot("spring-boot-starter-batch")
    boot("spring-boot-starter-test", test = true)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

fun DependencyHandlerScope.boot(name: String, test: Boolean = false) {
    if (test) {
        testImplementation("org.springframework.boot:$name")
    } else {
        implementation("org.springframework.boot:$name")
    }
}