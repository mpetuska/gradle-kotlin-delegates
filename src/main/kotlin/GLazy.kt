package dev.petuska.gradle.kotlin.delegates

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * TODO
 */
public interface GLazy<T> : ReadOnlyProperty<Any?, T>

/**
 * TODO
 */
@GLazyDsl
public fun <FROM, TO> GLazy<FROM>.convert(converter: (FROM) -> TO): GLazy<TO> = GLazyConverter(this, converter)

private open class GLazyConverter<FROM, TO>(
  private val source: GLazy<FROM>,
  private val converter: (FROM) -> TO,
) : GLazy<TO> {
  override fun getValue(thisRef: Any?, property: KProperty<*>): TO {
    return source.getValue(thisRef, property).let(converter)
  }
}

/**
 * TODO
 */
public interface GMutableLazy<T> : GLazy<T>, ReadWriteProperty<Any?, T>

/**
 * TODO
 */
@GLazyDsl
public fun <FROM, TO> GMutableLazy<FROM>.convert(converter: (FROM) -> TO, reverser: (TO) -> FROM): GMutableLazy<TO> =
  GLazyReverser(this, converter, reverser)

private class GLazyReverser<FROM, TO>(
  private val source: GMutableLazy<FROM>,
  converter: (FROM) -> TO,
  private val reverser: (TO) -> FROM,
) : GLazyConverter<FROM, TO>(source, converter), GMutableLazy<TO> {
  override fun setValue(thisRef: Any?, property: KProperty<*>, value: TO) {
    source.setValue(thisRef, property, reverser(value))
  }
}
