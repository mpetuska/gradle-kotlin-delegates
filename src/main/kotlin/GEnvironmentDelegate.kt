package dev.petuska.gradle.kotlin.delegates

import kotlin.reflect.KProperty

/**
 * TODO
 */
public class GEnvironmentDelegate<T>(
  prefix: String? = null,
  private val getEnv: (String) -> String? = System::getenv,
  private val converter: (String) -> T,
) : GLazy<T?> {
  private val lowerPrefix = prefix?.replace(separators, ".")?.removeSuffix(".")
    ?.takeIf(String::isNotBlank)?.lowercase()

  private val upperPrefix = lowerPrefix?.uppercase()
    ?.replace(separators, "_")

  override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
    val value = getEnv(upperPrefix?.plus("_${property.name.uppercase()}") ?: property.name.uppercase())
      ?: getEnv(lowerPrefix?.plus(".${property.name}") ?: property.name)
    return value?.let(converter)
  }

  public companion object {
    internal val separators = Regex("[.\\- _]")
    public operator fun invoke(
      prefix: String? = null
    ): GEnvironmentDelegate<String> = GEnvironmentDelegate(prefix) { it }
  }
}
