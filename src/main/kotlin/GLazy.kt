package dev.petuska.gradle.kotlin.delegates

import dev.petuska.gradle.kotlin.delegates.dsl.GLazyDsl
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

/**
 * Read-only lazy gradle value
 */
public typealias GLazy<T> = Provider<T>

/**
 * Non-nullable read-only lazy gradle value
 */
public interface GStrictLazy<T : Any> : GLazy<T>
internal class GStrictLazyImpl<T : Any>(base: GLazy<T>) : GStrictLazy<T>, GLazy<T> by base

/**
 * Read-write lazy gradle property
 */
public typealias GMutableLazy<T> = Property<T>

/**
 * Non-nullable Read-write lazy gradle property
 */
public interface GStrictMutableLazy<T : Any> : GStrictLazy<T>, GMutableLazy<T>
internal class GStrictMutableLazyImpl<T : Any>(base: GMutableLazy<T>) : GStrictMutableLazy<T>, GMutableLazy<T> by base

/**
 * Boxes given [GLazy] into [GStrictLazy] which throws an error on delegate access if no value is present
 * @receiver [GLazy] to box
 * @return [GStrictLazy] assuming non-nullable values
 */
@GLazyDsl
public fun <T : Any> GLazy<T>.strict(): GStrictLazy<T> = GStrictLazyImpl(this)

/**
 * Boxes given [GMutableLazy] into [GStrictMutableLazy] which throws an error on delegate
 * access if no value is present and prevents setting null values on delegate assignments.
 * @receiver [GMutableLazy] to box
 * @return [GStrictMutableLazy] assuming non-nullable values
 */
@GLazyDsl
public fun <T : Any> GMutableLazy<T>.strict(): GStrictMutableLazy<T> = GStrictMutableLazyImpl(this)
