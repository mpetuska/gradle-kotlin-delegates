package dev.petuska.gradle.kotlin.delegates

import org.gradle.api.Project
import kotlin.reflect.KProperty

private class GPropertyDelegate<T>(
  private val project: Project,
  prefix: String?,
  private val converter: (String) -> T,
) : GLazy<T?> {
  private val prefix = prefix?.trim()?.removeSuffix(".")?.takeIf(String::isNotBlank)
  override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
    val key = prefix?.plus(".${property.name}") ?: property.name
    return project.findProperty(key)?.toString()?.let(converter)
  }
}

/**
 * TODO
 */
@GLazyDsl
public fun <T> Project.gProperty(
  prefix: String? = null,
  converter: (String) -> T,
): GLazy<T?> = GPropertyDelegate(project = this, prefix = prefix, converter = converter)

/**
 * TODO
 */
@GLazyDsl
public fun Project.gProperty(
  prefix: String? = null,
): GLazy<String?> = gProperty(prefix = prefix, converter = { it })
