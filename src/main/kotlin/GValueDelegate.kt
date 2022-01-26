package dev.petuska.gradle.kotlin.delegates

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import kotlin.reflect.KProperty

/**
 * TODO
 */
@GLazyDsl
public inline fun <reified T : Any> ObjectFactory.lazyVal(
  crossinline valueProvider: () -> T,
): GLazy<T> = GValueDelegate {
  property(T::class.java).apply {
    convention(valueProvider())
  }
}

/**
 * TODO
 */
@GLazyDsl
public inline fun <reified T : Any> ObjectFactory.lazyVar(
  crossinline defaultProvider: () -> T,
): GMutableLazy<T> = GVariableDelegate {
  property(T::class.java).apply {
    convention(defaultProvider())
  }
}

/**
 * TODO
 */
@GLazyDsl
public inline fun <reified T : Any> ObjectFactory.lazyVal(value: T): GLazy<T> = lazyVal { value }

/**
 * TODO
 */
@GLazyDsl
public inline fun <reified T : Any> ObjectFactory.lazyVar(default: T): GMutableLazy<T> = lazyVar { default }

/**
 * TODO
 */
@GLazyDsl
public fun <T> Provider<T>.toGLazy(): GLazy<T> = GValueDelegate { this }

/**
 * TODO
 */
@GLazyDsl
public fun <T> Property<T>.toGMutableLazy(): GMutableLazy<T> = GVariableDelegate { this }

@PublishedApi
internal open class GValueDelegate<T, P : Provider<T>>(
  protected open val valueProvider: () -> P,
) : GLazy<T> {
  protected open var value: P? = null
  override fun getValue(thisRef: Any?, property: KProperty<*>): T {
    val v = value ?: valueProvider().also { value = it }
    return v.get()
  }
}

@PublishedApi
internal class GVariableDelegate<T>(
  override val valueProvider: () -> Property<T>,
) : GValueDelegate<T, Property<T>>(valueProvider), GMutableLazy<T> {
  override var value: Property<T>? = null
  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    val v = this.value ?: valueProvider().also { this.value = it }
    v.set(value)
  }
}
