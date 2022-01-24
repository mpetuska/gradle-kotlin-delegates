[![Slack chat](https://img.shields.io/badge/kotlinlang-green?logo=slack&style=flat-square)](https://kotlinlang.slack.com/team/UL1A5BA2X)
[![Dokka docs](https://img.shields.io/badge/docs-dokka-orange?style=flat-square)](http://mpetuska.github.io/gradle-kotlin-delegates)
[![Version gradle-plugin-portal](https://img.shields.io/maven-metadata/v?label=gradle%20plugin%20portal&logo=gradle&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fdev.petuska%2Fgradle-kotlin-delegates%2Fmaven-metadata.xml&style=flat-square)](https://plugins.gradle.org/plugin/dev.petuska.npm.publish)
[![Version maven-central](https://img.shields.io/maven-central/v/dev.petuska/gradle-kotlin-delegates?logo=apache-maven&style=flat-square)](https://mvnrepository.com/artifact/dev.petuska/gradle-kotlin-delegates/latest)

# gradle-kotlin-delegates

### GitHub Actions

The template also comes with GH actions to check builds on PRs and publish artefacts when creating a GH release. By
default, it'll publish to GH packages and Maven Central. However, to fully unlock Maven Central publishing, you'll need
to add these secrets to your GH repository. If you want to quickly disable Maven Central publishing, you can toggle it
at [./.github/workflows/release.yml#L80]`

* `SIGNING_KEY` - GPG signing key
* `SIGNING_KEY_ID` - GPG signing key ID
* `SIGNING_PASSWORD` - GPG signing key password (if set)
* `SONATYPE_PASSWORD` - Sonatype PAT username
* `SONATYPE_USERNAME` - Sonatype PAT password
