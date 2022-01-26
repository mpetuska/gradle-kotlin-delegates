plugins {
  kotlin("jvm") version "1.5.32"
  application
}

repositories {
  mavenCentral()
  google()
}

description = "Local consumer sandbox"

application {
  mainClass.set("local.sandbox.MainKt")
}

dependencies {
  implementation("dev.petuska:gradle-kotlin-delegates")
  implementation(gradleApi())
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "11"
    }
  }
}
