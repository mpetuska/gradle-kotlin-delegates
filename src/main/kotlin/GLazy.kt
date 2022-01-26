package dev.petuska.gradle.kotlin.delegates

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

/**
 * TODO
 */
public typealias GLazy<T> = ReadOnlyProperty<Any?, T>

/**
 * TODO
 */
public typealias GMutableLazy<T> = ReadWriteProperty<Any?, T>
