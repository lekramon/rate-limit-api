plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "br.com.lekramon"
version = "0.0.1-SNAPSHOT"
description = "Rate Limit service"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

dependencies {
	implementation(libs.spring.boot.starter.webmvc)
	implementation(libs.kotlin.reflect)
	implementation(libs.jackson.module.kotlin)
	testImplementation(platform(libs.cucumber.bom))
	testImplementation(libs.cucumber.java)
	testImplementation(libs.cucumber.junit.platform.engine)
	testImplementation(libs.cucumber.spring)
	testImplementation(libs.spring.boot.starter.webmvc.test)
	testImplementation(libs.kotlin.test.junit5)
	testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
