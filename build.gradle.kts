import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("kapt") version "1.4.20"
}
group = "me.z002rjs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    kapt("com.google.dagger:dagger-compiler:2.30.1")
    implementation("com.google.dagger:dagger:2.30.1")
    testImplementation(kotlin("test-junit"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}