package dev.petuska.gradle.kotlin.delegates

import kotlin.reflect.KProperty

@DslMarker
internal annotation class GLazyDsl

/**
 * TODO
 */
@GLazyDsl
public fun <FROM, TO> GLazy<FROM>.map(converter: (FROM) -> TO): GLazy<TO> = GLazyConverter(this, converter)

/**
 * TODO
 */
@GLazyDsl
public fun <FROM, TO> GMutableLazy<FROM>.map(converter: (FROM) -> TO, reverser: (TO) -> FROM): GMutableLazy<TO> =
  GLazyReverser(this, converter, reverser)

/**
 * TODO
 */
@GLazyDsl
public fun <T> GLazy<T>.filter(filter: (T) -> Boolean): GLazy<T?> = map { it.takeIf(filter) }

private open class GLazyConverter<FROM, TO>(
  private val source: GLazy<FROM>,
  private val converter: (FROM) -> TO,
) : GLazy<TO> {
  override fun getValue(thisRef: Any?, property: KProperty<*>): TO {
    return source.getValue(thisRef, property).let(converter)
  }
}

private class GLazyReverser<FROM, TO>(
  private val source: GMutableLazy<FROM>,
  converter: (FROM) -> TO,
  private val reverser: (TO) -> FROM,
) : GLazyConverter<FROM, TO>(source, converter), GMutableLazy<TO> {
  override fun setValue(thisRef: Any?, property: KProperty<*>, value: TO) {
    source.setValue(thisRef, property, reverser(value))
  }
}
