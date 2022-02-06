package dev.petuska.gradle.kotlin.delegates.dsl

import dev.petuska.gradle.kotlin.delegates.GLazy

@DslMarker
internal annotation class GLazyDsl

/**
 * Filters [GLazy] values on each request and defaults to `null` if the current value does not match the [filter]
 * @receiver [GLazy] to filter
 * @param filter to apply
 * @return [GLazy] that filters out values on request
 */
@GLazyDsl
public fun <T : Any> GLazy<T>.filter(filter: (T) -> Boolean): GLazy<T> = map {
  @Suppress("UNCHECKED_CAST")
  it.takeIf(filter) as T
}

/**
 * Converts a given [GLazy]<T> to [GLazy]<O> by applying converter on each value request.
 * @receiver [GLazy] to convert
 * @param converter to apply
 * @return [GLazy] with converted type
 */
@GLazyDsl
public fun <T : Any, O : Any> GLazy<T>.convert(converter: (T) -> O?): GLazy<O> {
  @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
  return map(converter)
}
