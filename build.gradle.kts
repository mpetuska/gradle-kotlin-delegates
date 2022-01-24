plugins {
  if (System.getenv("CI") == null) {
    id("plugin.git-hooks")
  }
  id("plugin.library-jvm")
  id("plugin.publishing")
  id("plugin.publishing-nexus")
  `java-gradle-plugin`
}

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
  }
}

dependencies {
  api(gradleApi())
}
