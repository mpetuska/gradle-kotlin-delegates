[![Slack chat](https://img.shields.io/badge/kotlinlang-green?logo=slack&style=flat-square)](https://kotlinlang.slack.com/team/UL1A5BA2X)
[![Dokka docs](https://img.shields.io/badge/docs-dokka-orange?style=flat-square)](http://mpetuska.github.io/gradle-kotlin-delegates)
[![Version maven-central](https://img.shields.io/maven-central/v/dev.petuska/gradle-kotlin-delegates?logo=apache-maven&style=flat-square)](https://mvnrepository.com/artifact/dev.petuska/gradle-kotlin-delegates/latest)

# gradle-kotlin-delegates

A bunch of kotlin delegates tailored to make
Gradle's [Lazy Configuration](https://docs.gradle.org/current/userguide/lazy_configuration.html) a lot more enjoyable
for both, plugin authors and consumers trying to configure them.

Certainly, `property = value` is much more appealing than `property.set(value)`.
