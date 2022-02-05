plugins {
  kotlin("jvm") version "1.5.32"
  application
}
apply<local.sandbox.TestPlugin>()
version = "0.0.0"
configure<local.sandbox.TestPlugin.TestExtension> {
  println("ASSERTING TEST EXTENSION")
  assert(baseProperty == null)
  assert(orGradle == version)
  assert(orSystem == System.getProperty("user.home"))
  assert(orEnv == System.getenv("HOME"))
  assert(orFallbackChain == orEnv)
  baseProperty = "NEW"
  assert(baseProperty == "NEW")
  assert(orGradle == "NEW")
  assert(orSystem == "NEW")
  assert(orEnv == "NEW")
  assert(orFallbackChain == "NEW")
  println("ASSERTION SUCCESSFUL")
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
  testImplementation(kotlin("test"))
}
