plugins {
  id("de.fayard.refreshVersions") version "0.50.2"
  id("com.gradle.enterprise") version "3.8.1"
}

refreshVersions {
  extraArtifactVersionKeyRules(rootDir.resolve("versions.rules"))
}

rootProject.name = "gradle-kotlin-delegates"
