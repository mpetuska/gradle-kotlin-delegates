package dev.petuska.gradle.kotlin.delegates

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.checkAll

class GEnvDelegateTest : WordSpec({
  val separators = Regex("[.\\- _]")
  "GEnvDelegate" `when` {
    fun String.asGetEnv() = { it: String ->
      if (it == this) {
        success("Key did match; expected=[$this], actual=[$it]")
        "true"
      } else {
        warning("Key did not match; expected=[$this], actual=[$it]")
        null
      }
    }

    fun test(prefix: String?, expectedKey: String) {
      info("prefix=[$prefix], expectedKey=[$expectedKey]")

      val target by gEnv(
        prefix = prefix,
        getEnv = expectedKey.asGetEnv(),
        converter = { it }
      )
      target shouldBe "true"
    }

    "given no prefix" should {
      "still work with raw key" {
        test(null, "target")
      }
      "still work with uppercase key" {
        test(null, "TARGET")
      }
    }
    "given various keys" should {
      checkAll(Arb.stringPattern("[A-Za-z0-9_. ]{0,100}")) { prefix ->
        "support raw keys" {
          val expectedKey = prefix.trim().replace(separators, "_")
            .removeSuffix("_").takeIf(String::isNotBlank)?.plus("_target") ?: "target"
          test(prefix, expectedKey)
        }
        "support uppercase names" {
          val expectedKey = prefix.trim().replace(separators, "_")
            .removeSuffix("_").takeIf(String::isNotBlank)?.plus("_TARGET") ?: "TARGET"
          test(prefix, expectedKey)
        }
        "support uppercase keys" {
          val expectedKey = prefix.trim().replace(separators, "_")
            .removeSuffix("_").uppercase().takeIf(String::isNotBlank)?.plus("_TARGET") ?: "TARGET"
          test(prefix, expectedKey)
        }
      }
    }
  }
})
