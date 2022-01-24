package dev.petuska.gradle.kotlin.delegates

import kotlin.reflect.KProperty

private class GEnvironmentDelegate<T>(
  prefix: String?,
  private val getEnv: (String) -> String?,
  private val converter: (String) -> T,
) : GLazy<T?> {
  private val prefix = prefix?.trim()?.replace(separators, "_")?.removeSuffix("_")
    ?.takeIf(String::isNotBlank)

  private val upperPrefix = this.prefix?.uppercase()

  override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
    val value = getEnv(prefix?.plus("_${property.name}") ?: property.name)
      ?: getEnv(prefix?.plus("_${property.name.uppercase()}") ?: property.name.uppercase())
      ?: getEnv(upperPrefix?.plus("_${property.name.uppercase()}") ?: property.name.uppercase())

    return value?.let(converter)
  }

  companion object {
    internal val separators = Regex("[.\\- _]")
  }
}

/**
 * TODO
 */
@GLazyDsl
public fun <T> gEnv(
  prefix: String? = null,
  getEnv: (String) -> String? = System::getenv,
  converter: (String) -> T,
): GLazy<T?> = GEnvironmentDelegate(prefix = prefix, getEnv = getEnv, converter = converter)

/**
 * TODO
 */
@GLazyDsl
public fun gEnv(
  prefix: String? = null,
  getEnv: (String) -> String? = System::getenv,
): GLazy<String?> = gEnv(prefix = prefix, getEnv = getEnv, converter = { it })
