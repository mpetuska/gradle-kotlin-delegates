package dev.petuska.gradle.kotlin.delegates

import dev.petuska.gradle.kotlin.delegates.dsl.GLazyDsl
import dev.petuska.gradle.kotlin.delegates.dsl.or
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ProviderFactory
import javax.inject.Inject

/**
 * Builds an instance of [GMutableLazy]
 * @param initial value if any
 * @return [GMutableLazy] instance
 */
@GLazyDsl
public inline fun <reified T : Any> WithGLazyFactory.gMutableLazy(initial: T? = null): GMutableLazy<T> =
  objectFactory.property(T::class.java).also {
    initial?.let(it::set)
  }

/**
 * Builds an instance of [GLazy]
 * @param value provider
 * @return [GLazy] instance
 */
@GLazyDsl
public fun <T : Any> WithGLazyFactory.gLazy(value: () -> T): GLazy<T> = providerFactory.provider(value)

/**
 * Extended version of [WithGLazy] providing additional DSL for customised [GLazy] fallbacks using [providerFactory].
 */
public interface WithGLazyFactory : WithGLazy {
  /**
   * Automatically injected [ProviderFactory] used to create new [GLazy] instances for chaining
   */
  @get:Inject
  public val providerFactory: ProviderFactory
    get() = error("ProviderFactory not injected")

/**
   * Automatically injected [ObjectFactory] used to create new [GMutableLazy] instances for chaining
   */
  @get:Inject
  public val objectFactory: ObjectFactory
    get() = error("ObjectFactory not injected")

/**
   * Registers a new [GLazy] fallback that will look up an environment variable if base value is missing.
   * @receiver [GLazy] providing base value
   * @param key to look up environment variable by
   * @param converter to apply when converting retrieved environment variable value to expected type
   * @return chained [GLazy] that falls back to environment value lookup
   */
  @GLazyDsl
  public fun <T : Any> GLazy<T>.orEnv(key: String, converter: (String) -> T): GLazy<T> =
    or(providerFactory.environmentVariable(key), converter)

/**
   * Registers a new [GLazy] fallback that will look up an environment variable if base value is missing.
   * @receiver [GLazy] providing base value
   * @param key to look up environment variable by
   * @return chained [GLazy] that falls back to environment value lookup
   */
  @GLazyDsl
  public infix fun GLazy<String>.orEnv(key: String): GLazy<String> = orEnv(key) { it }

/**
   * Registers a new [GLazy] fallback that will look up a gradle property if base value is missing.
   * @receiver [GLazy] providing base value
   * @param key to look up gradle property by
   * @param converter to apply when converting retrieved gradle property value to expected type
   * @return chained [GLazy] that falls back to gradle property lookup
   */
  @GLazyDsl
  public fun <T : Any> GLazy<T>.orGradleProperty(key: String, converter: (String) -> T): GLazy<T> =
    or(providerFactory.gradleProperty(key), converter)

/**
   * Registers a new [GLazy] fallback that will look up a gradle property if base value is missing.
   * @receiver [GLazy] providing base value
   * @param key to look up gradle property by
   * @return chained [GLazy] that falls back to gradle property lookup
   */
  @GLazyDsl
  public infix fun GLazy<String>.orGradleProperty(key: String): GLazy<String> = orGradleProperty(key) { it }

/**
   * Registers a new [GLazy] fallback that will look up a system property if base value is missing.
   * @receiver [GLazy] providing base value
   * @param key to look up system property by
   * @param converter to apply when converting retrieved system property value to expected type
   * @return chained [GLazy] that falls back to system property lookup
   */
  @GLazyDsl
  public fun <T : Any> GLazy<T>.orSystemProperty(key: String, converter: (String) -> T): GLazy<T> =
    or(providerFactory.systemProperty(key), converter)

/**
   * Registers a new [GLazy] fallback that will look up a system property if base value is missing.
   * @receiver [GLazy] providing base value
   * @param key to look up system property by
   * @return chained [GLazy] that falls back to system property lookup
   */
  @GLazyDsl
  public infix fun GLazy<String>.orSystemProperty(key: String): GLazy<String> = orSystemProperty(key) { it }

/**
   * Registers a new [GLazy] fallback that will look up an env variable then system property and finally gradle property if base value is missing.
   * @receiver [GLazy] providing base value
   * @param envKey to look up environment variable by
   * @param systemKey to look up system property by, defaults to lowercase [envKey] with `_` replaced to `.`
   * @param gradleKey to look up gradle property by, [systemKey]
   * @param converter to apply when converting retrieved property value to expected type
   * @return chained [GLazy] that falls back to property chain lookup
   */
  @GLazyDsl
  public fun <T : Any> GLazy<T>.orEnvSystemGradlePropertyChain(
    envKey: String,
    systemKey: String = envKey.lowercase().replace("_", "."),
    gradleKey: String = systemKey,
    converter: (String) -> T
  ): GLazy<T> = orEnv(envKey, converter).orSystemProperty(systemKey, converter).orGradleProperty(gradleKey, converter)

/**
   * Registers a new [GLazy] fallback that will look up an env variable then system property and finally gradle property if base value is missing.
   * @receiver [GLazy] providing base value
   * @param envKey to look up environment variable by
   * @param systemKey to look up system property by, defaults to lowercase [envKey] with `_` replaced to `.`
   * @param gradleKey to look up gradle property by, [systemKey]
   * @return chained [GLazy] that falls back to property chain lookup
   */
  @GLazyDsl
  public fun GLazy<String>.orEnvSystemGradlePropertyChain(
    envKey: String,
    systemKey: String = envKey.lowercase().replace("_", "."),
    gradleKey: String = systemKey,
  ): GLazy<String> = orEnvSystemGradlePropertyChain(envKey, systemKey, gradleKey) { it }

/**
   * Registers a new [GLazy] fallback that will look up an env variable then system property and finally gradle property if base value is missing.
   * System key is implied as `[envKey].lowercase().replace("_", ".")`
   * Gradle key is implied as `[envKey].lowercase().replace("_", ".")`
   * @receiver [GLazy] providing base value
   * @param envKey to look up environment variable by and build other keys from
   * @return chained [GLazy] that falls back to property chain lookup
   */
  @GLazyDsl
  public infix fun GLazy<String>.orEnvSystemGradlePropertyChain(
    envKey: String,
  ): GLazy<String> =
    orEnvSystemGradlePropertyChain(
      envKey = envKey,
      systemKey = envKey.lowercase().replace("_", "."),
      gradleKey = envKey.lowercase().replace("_", ".")
    )
}
