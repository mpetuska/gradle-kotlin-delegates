package dev.petuska.gradle.kotlin.delegates

@DslMarker
annotation class TestUtilDsl

@TestUtilDsl
fun warning(msg: Any?) = println("❌ $msg")

@TestUtilDsl
fun success(msg: Any?) = println("✅ $msg")

@TestUtilDsl
fun info(msg: Any?) = println("ℹ️ $msg")
