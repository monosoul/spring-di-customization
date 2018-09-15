group = "com.github.monosoul"
version = "1.0"

val springBootVersion: String by extra

buildscript {
    val springBootVersion by extra { "2.0.5.RELEASE" }

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

plugins {
    java
}

apply {
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val junitVersion by extra { "5.3.1" }
dependencies {
    compile("org.springframework.boot:spring-boot-starter-aop")
    compile("org.springframework.boot:spring-boot-starter-web")
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude("junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testCompile("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntime("org.junit.vintage:junit-vintage-engine:$junitVersion")
    testCompile("org.assertj:assertj-core:3.9.1")
    testCompile("org.mockito:mockito-core:2.21.0")
    compile("org.projectlombok:lombok:1.18.2")
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}

repositories {
    mavenCentral()
}