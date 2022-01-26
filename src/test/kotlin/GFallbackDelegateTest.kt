package dev.petuska.gradle.kotlin.delegates

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.checkAll
import org.gradle.testfixtures.ProjectBuilder

class GFallbackDelegateTest : WordSpec({
  val separators = Regex("[.\\- _]")
  "GFallbackDelegate" `when` {
    fun String?.asGetEnv() = { it: String ->
      if (this?.startsWith(it) == true) {
        success("Key did match; expected=[$this], actual=[$it]")
        it
      } else {
        warning("Key did not match; expected=[$this], actual=[$it]")
        null
      }
    }

    fun test(prefix: String?, expectedKey1: String?, expectedKey2: String? = null, default: String? = null) {
      info("prefix=[$prefix], expectedKey1=[$expectedKey1], expectedKey2=[$expectedKey2], default=[$default]")
      val project = ProjectBuilder.builder().build().also { prj ->
        expectedKey1?.let {
          prj.extensions.extraProperties[it] = it
          prj.extensions.extraProperties["${it}2"] = it
        }
      }
      val base: GLazy<String?> = project.lazyProperty(prefix)
      val fallback: GLazy<String?> = lazyEnv(prefix, expectedKey2.asGetEnv())
      run {
        val target: String? by base or fallback or default
        target shouldBe (expectedKey1 ?: expectedKey2 ?: default)
      }
      run {
        val target: String? by base or fallback or { default }
        target shouldBe (expectedKey1 ?: expectedKey2 ?: default)
      }
      success("OK")
    }
    "given no prefix" should {
      "still work on base" {
        test(null, "target")
      }
      "still work on fallback" {
        test(null, null, "TARGET")
      }
      "still work on constant fallback" {
        test(null, null, null, "CONSTANT")
      }
    }
    "given various keys" should {
      checkAll(Arb.stringPattern("[A-Za-z0-9_. ]{0,100}")) { prefix ->
        val expectedKey1 = prefix.trim().removeSuffix(".")
          .takeIf(String::isNotBlank)?.plus(".target") ?: "target"
        val expectedKey2 = prefix.trim().replace(separators, "_")
          .removeSuffix("_").uppercase().takeIf(String::isNotBlank)?.plus("_TARGET") ?: "TARGET"

        "work on base" {
          test(prefix, expectedKey1)
        }
        "work on fallback" {
          test(prefix, null, expectedKey2)
        }
        "work on constant fallback" {
          test(null, null, null, "CONSTANT")
        }
      }
    }
  }
})
