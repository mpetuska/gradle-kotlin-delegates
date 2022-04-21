plugins {
  id("de.fayard.refreshVersions") version "0.40.0"
  id("com.gradle.enterprise") version "3.10"
}

refreshVersions {
  extraArtifactVersionKeyRules(rootDir.resolve("versions.rules"))
}

rootProject.name = "gradle-kotlin-delegates"
