package dev.petuska.gradle.kotlin.delegates

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

/**
 * TODO
 */
public interface GLazy<T> : ReadOnlyProperty<Any?, T>

/**
 * TODO
 */
public interface GMutableLazy<T> : GLazy<T>, ReadWriteProperty<Any?, T>
