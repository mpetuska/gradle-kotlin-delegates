package dev.petuska.gradle.kotlin.delegates

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import kotlin.reflect.KProperty

/**
 * TODO
 */
@GLazyDsl
public inline fun <reified T> ObjectFactory.lazyVal(
  default: T? = null,
): GLazy<T> = property(T::class.java).apply {
  convention(default)
}.toGLazy()

/**
 * TODO
 */
@GLazyDsl
public inline fun <reified T> ObjectFactory.lazyVar(
  default: T? = null,
): GMutableLazy<T> = property(T::class.java).apply {
  convention(default)
}.toGMutableLazy()

/**
 * TODO
 */
@GLazyDsl
public fun <T> Provider<T>.toGLazy(): GLazy<T> = GValueDelegate(this)

/**
 * TODO
 */
@GLazyDsl
public fun <T> Property<T>.toGMutableLazy(): GMutableLazy<T> = GVariableDelegate(this)

private open class GValueDelegate<T>(
  private val value: Provider<T>,
) : GLazy<T> {
  override fun getValue(thisRef: Any?, property: KProperty<*>): T = value.get()
}

private class GVariableDelegate<T>(
  private val value: Property<T>,
) : GValueDelegate<T>(value), GMutableLazy<T> {
  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    this.value.set(value)
  }
}
