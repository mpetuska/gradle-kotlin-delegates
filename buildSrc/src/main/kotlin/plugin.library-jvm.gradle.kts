import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
  id("plugin.common")
  id("dev.petuska.klip")
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
  explicitApi()
  sourceSets {
    test {
      dependencies {
        implementation("io.kotest:kotest-runner-junit5:_")
        implementation("io.kotest:kotest-framework-datatest:_")
        implementation("io.kotest:kotest-property:_")
        implementation("dev.petuska:klip:_")
      }
      languageSettings {
        optIn("io.kotest.common.ExperimentalKotest")
      }
    }
  }
}

tasks {
  project.properties["org.gradle.project.targetCompatibility"]?.toString()?.let {
    withType<KotlinCompile> { kotlinOptions { jvmTarget = it } }
  }
  withType<Test> { useJUnitPlatform() }
}
