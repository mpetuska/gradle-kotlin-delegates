package dev.petuska.gradle.kotlin.delegates

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * TODO
 */
public interface GLazy<T> : ReadOnlyProperty<Any?, T> {
  public fun <N> convert(converter: (T) -> N): GLazy<N> = GLazyConverter(this, converter)
}

private open class GLazyConverter<T, N>(
  private val source: GLazy<T>,
  private val converter: (T) -> N,
) : GLazy<N> {
  override fun getValue(thisRef: Any?, property: KProperty<*>): N {
    return source.getValue(thisRef, property).let(converter)
  }
}

/**
 * TODO
 */
public interface GMutableLazy<T> : GLazy<T>, ReadWriteProperty<Any?, T> {
  public fun <N> convert(converter: (T) -> N, reverser: (N) -> T): GMutableLazy<N> =
    GLazyReverser(this, converter, reverser)
}

private class GLazyReverser<T, N>(
  private val source: GMutableLazy<T>,
  converter: (T) -> N,
  private val reverser: (N) -> T,
) : GLazyConverter<T, N>(source, converter), GMutableLazy<N> {
  override fun setValue(thisRef: Any?, property: KProperty<*>, value: N) {
    source.setValue(thisRef, property, reverser(value))
  }
}
