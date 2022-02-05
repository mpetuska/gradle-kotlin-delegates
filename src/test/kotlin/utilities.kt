package dev.petuska.gradle.kotlin.delegates

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.testfixtures.ProjectBuilder

@DslMarker
annotation class TestUtilDsl

@TestUtilDsl
fun warning(msg: Any?) = println("❌ $msg")

@TestUtilDsl
fun success(msg: Any?) = println("✅ $msg")

@TestUtilDsl
fun info(msg: Any?) = println("ℹ️ $msg")

@TestUtilDsl
suspend fun withProject(
  init: (ProjectBuilder.() -> Unit)? = null,
  action: (suspend Project.() -> Unit)
): Project = ProjectBuilder.builder().also { init?.invoke(it) }.build().also { it.action() }

@TestUtilDsl
inline fun <reified T> ExtensionContainer.configure(crossinline action: T.() -> Unit) {
  configure(T::class.java) {
    action(it)
  }
}
