package dev.petuska.gradle.kotlin.delegates

import org.gradle.api.Project
import kotlin.reflect.KProperty

/**
 * TODO
 */
@GLazyDsl
public fun <T> Project.lazyProperty(
  prefix: String? = null,
  converter: (String) -> T,
): GLazy<T?> = GPropertyDelegate(project = this, prefix = prefix, converter = converter)

/**
 * TODO
 */
@GLazyDsl
public fun Project.lazyProperty(
  prefix: String? = null,
): GLazy<String?> = lazyProperty(prefix = prefix, converter = { it })

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
