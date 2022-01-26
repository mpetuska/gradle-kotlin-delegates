[![Slack chat](https://img.shields.io/badge/kotlinlang-green?logo=slack&style=flat-square)](https://kotlinlang.slack.com/team/UL1A5BA2X)
[![Dokka docs](https://img.shields.io/badge/docs-dokka-orange?style=flat-square)](http://mpetuska.github.io/gradle-kotlin-delegates)
[![Version maven-central](https://img.shields.io/maven-central/v/dev.petuska/gradle-kotlin-delegates?logo=apache-maven&style=flat-square)](https://mvnrepository.com/artifact/dev.petuska/gradle-kotlin-delegates/latest)

# gradle-kotlin-delegates

A bunch of kotlin delegates tailored to make
Gradle's [Lazy Configuration](https://docs.gradle.org/current/userguide/lazy_configuration.html) a lot more enjoyable
for both, plugin authors and consumers trying to configure them.

Certainly, `property = value` is much more appealing than `property.set(value)`.

> The library was compiled against Gradle 7.3.3 & JDK 11

## Installation

The project is published to Maven Central and can be obtained from it via gradle coordinates

```kotlin
repositories {
  mavenCentral()
}

dependencies {
  api("dev.petuska:gradle-kotlin-delegates:_")
}
```

## Usage

Here are some of the sample usages of the delegates provided by the project

```kotlin
open class MyPluginExtension @Inject constructor(
  objectFactory: ObjectFactory,
  project: Project,
) {
  val value: Int by objectFactory.lazyVal {
    // Do some calculations of the value to be executed at the last minute to construct the gradle Provider<T> instance
    69
  }
  var configurationProperty: String by objectFactory.lazyVar("Default value to be used if nothing (or null) is set")
  var lazyConfigurationProperty: Int by objectFactory.lazyVar {
    "Default value to be used if nothing (or null) is set, will be computed on first interaction with the property"
    420
  }
  
  // Will look-up "my.custom.gradleProp" in gradle properties each time it's queried
  val gradleProp: String? by project.lazyProperty(prefix = "my.custom")
  
  // Will look-up any of ["MY_CUSTOM_ENVVAR", "MY_cUSTOm_ENVVAR", "MY_cUSTOm_envVar"] in environment properties each time it's queried
  val envVar: String? by lazyEnv(prefix = "MY_cUSTOm")
  
  // Will chain through each delegate until it finds a value each time it's queried, 
  // stopping at constant "DEFAULT" if nothing is found beforehand
  val chainedFallbacks: String by project.lazyProperty(prefix = "my.custom") or lazyEnv(prefix = "MY_cUSTOm") or "DEFAULT"
  // Will chain through each delegate until it finds a value each time it's queried, 
  // stopping after calculating default value by invoking constant lambda provider if nothing is found beforehand
  val chainedFallbacks: String by project.lazyProperty(prefix = "my.custom") or lazyEnv(prefix = "MY_cUSTOm") or "DEFAULT"
}
```
