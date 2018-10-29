import com.palantir.gradle.docker.DockerExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.types.Description
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.gradle.api.tasks.testing.Test

val kotlin_version: String by extra


buildscript {

    var kotlin_version: String by extra
    kotlin_version = "1.2.31"
    extra["kotlinVersion"] = "1.2.31"
    extra["springBootVersion"] = "2.0.1.RELEASE"
    extra["boostrapVersion"] = "3.3.6"
    extra["jQueryVersion"] = "2.2.4"
    extra["jQueryUIVersion"] = "1.11.4"
    extra["elVersion"] = "3.0.1-b08"
    val shadowPluginVer = "2.0.1"

    val springBootVersion: String by extra

    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.1.0-RC1")
        classpath("gradle.plugin.com.palantir.gradle.docker:gradle-docker:0.19.2")
        classpath("com.github.jengelman.gradle.plugins:shadow:$shadowPluginVer")
        classpath("org.owasp:dependency-check-gradle:3.1.2")

    }
}



plugins {
    val kotlinVersion = "1.2.21"
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.4.RELEASE"

}

apply {
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
    plugin("kotlin")
    plugin("java")
    plugin("com.palantir.docker")
    plugin("com.github.johnrengelman.shadow")
    plugin("org.owasp.dependencycheck")
    plugin("jacoco")
}



val build: DefaultTask by tasks
val bootJar = tasks["bootJar"] as BootJar
build.dependsOn(bootJar)

configure<DockerExtension> {
    name = "app"
    files(bootJar.outputs)
    setDockerfile(file("Dockerfile"))
    buildArgs(mapOf(
            "PORT"   to  "8080",
            "JAVA_OPTS" to "-Xms64m -Xmx128m"
    ))
    pull(true)
    dependsOn(bootJar, tasks["jar"])
}

tasks.withType<BootJar> {
    baseName = "app"
    classifier = null
    version = null
    mainClassName = "com.jvmtech.ccddef.ExcelToCSVKt"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

val kotlinVersion: String by extra
val springBootVersion: String by extra
val jUnitVersion: String by extra
val boostrapVersion: String by extra
val jQueryVersion: String by extra
val jQueryUIVersion: String by extra
val elVersion: String by extra

version = "1.0.0-SNAPSHOT"

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
    withType<Jar> {
        enabled = true
        manifest {
            attributes["Implementation-Title"] = "CCD Definition CSV"
            attributes["Implementation-Version"] = "1.0.0-SNAPSHOT"
        }
    }
    withType<JacocoReport> {
        reports.xml.setEnabled(true)
        reports.html.setEnabled(true)
    }
}


repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("javax.cache:cache-api")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.webjars:webjars-locator:0.32-1")
    implementation("org.webjars:jquery:$jQueryVersion")
    implementation("org.webjars:jquery-ui:$jQueryUIVersion")
    implementation("org.webjars:bootstrap:$boostrapVersion")
    implementation("org.apache.commons:commons-csv:1.5")
    implementation("io.springfox:springfox-swagger2:2.7.0")
    implementation("io.springfox:springfox-swagger-ui:2.7.0")
    implementation("org.apache.poi:poi-ooxml:3.17")
    implementation("com.github.ajalt:clikt:1.5.0")


    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testImplementation("org.glassfish:javax.el:$elVersion")
    testImplementation("com.natpryce:hamkrest:1.4.2.2")
    testImplementation("org.springframework.security:spring-security-test") {
        exclude(module = "junit")
    }

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    runtimeOnly("org.hsqldb:hsqldb")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
