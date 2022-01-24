package dev.petuska.gradle.kotlin.delegates

import kotlin.reflect.KProperty

private open class GFallbackDelegate<T>(
  private val base: GLazy<T?>,
  private val fallback: GLazy<T>,
) : GLazy<T> {
  override fun getValue(thisRef: Any?, property: KProperty<*>): T {
    return base.getValue(thisRef, property) ?: fallback.getValue(thisRef, property)
  }
}

/**
 * TODO
 */
@GLazyDsl
public fun <T, F> GLazy<T?>.or(fallback: GLazy<F>, converter: (F) -> T): GLazy<T> =
  GFallbackDelegate(base = this, fallback = fallback.convert(converter))

/**
 * TODO
 */
@GLazyDsl
public infix fun <T> GLazy<T?>.or(fallback: GLazy<T>): GLazy<T> = or(fallback = fallback, converter = { it })

private class GMutableFallbackDelegate<T>(
  private val base: GMutableLazy<T?>,
  fallback: GLazy<T>,
) : GFallbackDelegate<T>(base, fallback), GMutableLazy<T> {
  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    base.setValue(thisRef, property, value)
  }
}

/**
 * TODO
 */
@GLazyDsl
public fun <T, F> GMutableLazy<T?>.or(fallback: GLazy<F>, converter: (F) -> T): GMutableLazy<T> =
  GMutableFallbackDelegate(base = this, fallback = fallback.convert(converter))

/**
 * TODO
 */
@GLazyDsl
public infix fun <T> GMutableLazy<T?>.or(fallback: GLazy<T>): GMutableLazy<T> =
  or(fallback = fallback, converter = { it })
