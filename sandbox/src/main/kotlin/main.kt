package local.sandbox

import dev.petuska.gradle.kotlin.delegates.lazyEnv
import dev.petuska.gradle.kotlin.delegates.lazyProperty
import dev.petuska.gradle.kotlin.delegates.lazyVal
import dev.petuska.gradle.kotlin.delegates.lazyVar
import dev.petuska.gradle.kotlin.delegates.or
import org.gradle.testfixtures.ProjectBuilder

fun main() {
  val project = ProjectBuilder.builder().build()
  val home by lazyEnv()
  val version by project.lazyProperty()
  val nonExistent by project.lazyProperty()
  val shell by project.lazyProperty() or lazyEnv()
  val fallback2 by project.lazyProperty() or "FALLBACK"
  println(
    """
    home=$home
    version=$version
    nonExistent=$nonExistent
    shell=$shell
    fallback2=$fallback2
    """.trimIndent()
  )
  val provider by project.objects.lazyVal("DEFAULT")
  println(
    """
    provider=$provider
    """.trimIndent()
  )

  var property by project.objects.lazyVar("DEFAULT")
  println(
    """
    property=$property
    """.trimIndent()
  )
  property = "OVERRIDDEN"
  println(
    """
    property=$property
    """.trimIndent()
  )
}
