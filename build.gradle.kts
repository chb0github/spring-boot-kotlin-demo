import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
		maven("https://repo.spring.io/milestone")
	}

	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.0.RC2")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.21")
		classpath("org.junit.platform:junit-platform-gradle-plugin:1.1.0")
	}
}

apply {
	plugin("org.springframework.boot")
	plugin("org.junit.platform.gradle.plugin")
}

plugins {
	val kotlinVersion = "1.2.21"
	id("org.jetbrains.kotlin.jvm") version kotlinVersion
	id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
	id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
	id("io.spring.dependency-management") version "1.0.4.RELEASE"
}

version = "1.0.0-SNAPSHOT"

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "1.8"
			freeCompilerArgs = listOf("-Xjsr305=strict")
		}
	}
}

repositories {
	mavenCentral()
	maven("http://repo.spring.io/milestone")
}

dependencies {
	compile("org.springframework.boot:spring-boot-starter-web")
	compile("org.springframework.boot:spring-boot-starter-data-jpa")
	compile("org.springframework.boot:spring-boot-starter-data-rest")
	compile("org.springframework.boot:spring-boot-starter-hateoas")
	compile("org.springframework.boot:spring-boot-starter-validation")
	compile("org.springframework.data:spring-data-rest-hal-browser:3.0.3.RELEASE")
	compile("org.springframework.boot:spring-boot-starter-actuator")
	compile("org.hibernate:hibernate-java8")

	// both dbs are supported. Change this when you're production ready
	compile("com.h2database:h2")

//	compile("org.hsqldb:hsqldb:2.3.2")
	compile("mysql:mysql-connector-java:5.1.40")

	compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	compile("org.jetbrains.kotlin:kotlin-reflect")
	compile("com.fasterxml.jackson.module:jackson-module-kotlin")
	compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	testCompile("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
	}
	testCompile("javax.xml.bind:jaxb-api:2.3.0")
	testCompile("org.junit.jupiter:junit-jupiter-api")
	testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

