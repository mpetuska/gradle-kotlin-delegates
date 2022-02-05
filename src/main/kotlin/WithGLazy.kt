package dev.petuska.gradle.kotlin.delegates

import dev.petuska.gradle.kotlin.delegates.dsl.GLazyDsl
import kotlin.reflect.KProperty

/**
 * A scoping interface providing [GLazy] delegate access functions
 */
public interface WithGLazy {
  @GLazyDsl
  public operator fun <T : Any> GLazy<T>.getValue(thisRef: Any?, property: KProperty<*>): T? = orNull

  @GLazyDsl
  public operator fun <T : Any> GStrictLazy<T>.getValue(thisRef: Any?, property: KProperty<*>): T = get()

  @GLazyDsl
  public operator fun <T : Any> GMutableLazy<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    set(value)
  }

  @GLazyDsl
  public operator fun <T : Any> GStrictMutableLazy<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    set(value)
  }
}
