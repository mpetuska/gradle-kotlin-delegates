package dev.petuska.gradle.kotlin.delegates

import org.gradle.api.Project
import kotlin.reflect.KProperty

/**
 * TODO
 */
public class GEnvironmentDelegate<T>(
  private val project: Project,
  prefix: String? = null,
  private val converter: (String) -> T,
) : GLazy<T?> {
  private val prefix = prefix?.lowercase()?.replace(separators, ".")
    ?.removeSurrounding(".")
  private val upperPrefix = prefix?.uppercase()?.replace(separators, "_")
  override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
    val value = project.findProperty(upperPrefix?.plus("_${property.name.uppercase()}") ?: property.name.uppercase())
      ?: project.findProperty(prefix?.plus(".${property.name}") ?: property.name)
    return value?.toString()?.let(converter)
  }

  public companion object {
    private val separators = Regex("[.\\- _]")
    public operator fun invoke(
      project: Project,
      prefix: String? = null
    ): GEnvironmentDelegate<String> = GEnvironmentDelegate(project, prefix) { it }
  }
}
