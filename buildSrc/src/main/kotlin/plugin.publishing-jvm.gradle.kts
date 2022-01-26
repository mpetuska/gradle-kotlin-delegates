import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("plugin.publishing")
}

val mainHostSpec: Spec<in Task> = Spec { !CI || isMainHost }

tasks {
  withType<KotlinCompile> {
    onlyIf(mainHostSpec)
    inputs.property("project.mainOS", project.property("project.mainOS"))
  }
}

afterEvaluate {
  publishing {
    publications {
      all {
        val targetPublication = this@all
        tasks.withType<AbstractPublishToMaven>()
          .matching { it.publication == targetPublication }
          .configureEach {
            onlyIf(mainHostSpec)
          }
        tasks.withType<GenerateModuleMetadata>()
          .matching { it.publication.get() == targetPublication }
          .configureEach {
            onlyIf(mainHostSpec)
          }
      }
    }
  }
}
