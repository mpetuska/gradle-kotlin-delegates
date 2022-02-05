package dev.petuska.gradle.kotlin.delegates.dsl

import dev.petuska.gradle.kotlin.delegates.GLazy

/**
 * Registers [other] as a fallback to use if `this` [GLazy] has no value on request.
 * @receiver [GLazy] providing base value
 * @param other [GLazy] to use when there is no base value
 * @return chained [GLazy] that falls back to [other] on missing base values
 */
@GLazyDsl
public infix fun <T : Any> GLazy<T>.or(other: GLazy<T>): GLazy<T> = orElse(other)

/**
 * Registers [other] as a fallback to use if `this` [GLazy] has no value on request.
 * @receiver [GLazy] providing base value
 * @param other [GLazy] to use when there is no base value
 * @param converter to apply on [other] values when converting them to base type
 * @return chained [GLazy] that falls back to [other] on missing base values
 */
@GLazyDsl
public fun <T : Any, O : Any> GLazy<T>.or(other: GLazy<O>, converter: (O) -> T?): GLazy<T> =
  or(other.convert(converter))

/**
 * Registers [default] value as a fallback to use if `this` [GLazy] has no value on request.
 * @receiver [GLazy] providing base value
 * @param default value to use when there is no base value
 * @return chained [GLazy] that falls back to [default] value on missing base values
 */
@GLazyDsl
public infix fun <T : Any> GLazy<T>.or(default: T): GLazy<T> = orElse(default)
