import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "2.0.0"
	kotlin("plugin.jpa") version "2.0.0" // for no-arg constructors
	kotlin("plugin.spring") version "2.0.0" // for all-open @Configuration classes
	kotlin("plugin.serialization") version "2.0.0" // for kotlinx.serialization

	idea
}

group = "com.rnimour.trials"
description = "Game Tracker app with CRUD operations"
version = "1.0.0-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.zalando:problem-spring-web:0.29.1")
	// module for ProblemModule (to register it in the ObjectMapper bean)
	implementation("org.zalando:jackson-datatype-problem:0.27.1")
	// to make Jackson (de)serialization work seamlessly with Kotlin, add kotlinModule() to the ObjectMapper bean
	// Previously, a default constructor must have existed, but now singel constructor classes can be used automatically
	// see https://github.com/FasterXML/jackson-module-kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.+")

	// for Kotlinx serialization
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

	// in-memory database
	runtimeOnly("com.h2database:h2")

	// testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("io.mockk:mockk:1.13.11")
	// For whenever instead of `when`
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
	// for easy JSON (de)serializing in test, though maybe just use kotlinx.serialization? Let's try both (
	testImplementation("com.google.code.gson:gson:2.8.9")
	// Try out TestContainers for integration tests
	testImplementation("org.testcontainers:testcontainers:1.20.0")
	testImplementation("org.testcontainers:junit-jupiter:1.20.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	// Hibernate needs to be able to subclass entities to add lazy-loading proxies
	annotation("jakarta.persistence.Entity")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks {
	test {
		testLogging {
			// how else could we solve exceptions without their stack traces?
			showStackTraces = true
			exceptionFormat = TestExceptionFormat.FULL
			// to see output of println
			events(TestLogEvent.STANDARD_OUT)
			// showStandardStreams = true
		}
	}
}


idea {
	module {
		isDownloadSources = true
	}
}