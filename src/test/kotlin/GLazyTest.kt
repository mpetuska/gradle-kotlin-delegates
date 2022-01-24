package dev.petuska.gradle.kotlin.delegates

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import kotlin.reflect.KProperty

class GLazyTest : WordSpec({
  "GLazy" should {
    val base = object : GLazy<String> {
      var current = "false"
      override fun getValue(thisRef: Any?, property: KProperty<*>): String = current
    }
    "be able to support conversion" {
      val target by base.convert(converter = String::toBoolean)
      base.current = "false"
      target shouldBe false
      base.current = "true"
      target shouldBe true
    }
  }
  "GMutableLazy" should {
    val base = object : GMutableLazy<String> {
      var current = "false"
      override fun getValue(thisRef: Any?, property: KProperty<*>): String = current
      override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        current = value
      }
    }
    "be able to support conversion and reversion" {
      var target by base.convert(converter = String::toBoolean, reverser = Boolean::toString)
      base.current = "false"
      target shouldBe false
      target = true
      target shouldBe true
      base.current shouldBe "true"
    }
  }
})
