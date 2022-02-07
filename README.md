[![Slack chat](https://img.shields.io/badge/kotlinlang-green?logo=slack&style=flat-square)](https://kotlinlang.slack.com/team/UL1A5BA2X)
[![Dokka docs](https://img.shields.io/badge/docs-dokka-orange?style=flat-square)](http://mpetuska.github.io/gradle-kotlin-delegates)
[![Version maven-central](https://img.shields.io/maven-central/v/dev.petuska/gradle-kotlin-delegates?logo=apache-maven&style=flat-square)](https://mvnrepository.com/artifact/dev.petuska/gradle-kotlin-delegates/latest)

# gradle-kotlin-delegates

A bunch of kotlin delegates tailored to make
Gradle's [Lazy Configuration](https://docs.gradle.org/current/userguide/lazy_configuration.html) a lot more enjoyable
for both, plugin authors and consumers trying to configure them.

Certainly, `property = value` is much more appealing than `property.set(value)`.

> The library was compiled against Gradle 7.3.3 & JDK 8

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

The main entrypoints are `WithGLazy` interface or its superset `WithGLazyFactory` scoping interfaces that you can apply
to your extensions. Main use-case is intended for more convenient plugin extensions API, however chained providers can
also be exposed in other gradle entities such as task outputs.

Here are some of the sample usages of the delegates provided by the project:

```kotlin
open class MyPluginExtension : WithGLazyFactory {
  // Backing gradle property to be tracked
  @get:Input
  internal val _property: Property<String> = gMutableLazy<String>()
  
  // Internal source-of-truth which will resolve either a property set by user, environment variable `MY_PROPERTY`,
  // system property `my.property`, gradle property `myProperty` or "DEFAULT" value, whichever comes first.
  private val _chainedFallbackValue: String by _property orEnv "MY_PROPERTY" orSystemProperty "my.property" orGradleProperty "myProperty" or "DEFAULT"
  
  // Equivalent to `_chainedFallbackValue`
  private val _chainedFallbackValue2: String by _property.orEnvSystemGradlePropertyChain(
    "MY_PROPERTY",
    "my.property",
    "myProperty"
  ) or "DEFAULT"
  
  // Similar to the above. Both, system and gradle keys will be inferred as "my.property"
  private val _chainedFallbackValue3: String by _property orEnvSystemGradlePropertyChain "MY_PROPERTY" or "DEFAULT"
  
  // DSL property exposed to the consumer, backed by `_property`. 
  // Makes use of implicit kotlin delegates to enable `value = "user-value"` syntax
  var value: String? by _property
  
  // Strict DSL property exposed to the consumer, backed by `_property`. "Strict" mode prevents the end-user from setting nullable values.
  // Makes use of implicit kotlin delegates to enable `value = "user-value"` syntax
  var strictValue: String by _property.strict()
}
```

The example above is using Strings for simplicity, but arbitrary types are supported everywhere with the use
of `GLazy::convert` utility to map between them.

```kotlin
open class MyPluginExtension : WithGLazyFactory {
  @get:Input
  internal val _property: Property<Int> = gMutableLazy<Int>()
  
  @get:Input
  internal val _anotherProperty: Property<String> = gMutableLazy<String>()
  
  internal val _chainedFallbackValue: Int by _property or _anotherProperty.convert(String::toInt)
}
```
