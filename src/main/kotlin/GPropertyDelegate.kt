package dev.petuska.gradle.kotlin.delegates

import org.gradle.api.Project
import kotlin.reflect.KProperty

/**
 * TODO
 */
public class GPropertyDelegate<T>(
  private val project: Project,
  prefix: String? = null,
  private val converter: (String) -> T,
) : GLazy<T?> {
  private val prefix = prefix?.removeSurrounding(".")?.plus(".")
  override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
    val key = prefix?.plus(property.name) ?: property.name
    return project.findProperty(key)?.toString()?.let(converter)
  }

  public companion object {
    public operator fun invoke(
      project: Project,
      prefix: String? = null
    ): GPropertyDelegate<String> = GPropertyDelegate(project, prefix) { it }
  }
}
