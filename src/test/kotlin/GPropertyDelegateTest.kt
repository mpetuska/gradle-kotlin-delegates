package dev.petuska.gradle.kotlin.delegates

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.checkAll
import org.gradle.testfixtures.ProjectBuilder

class GPropertyDelegateTest : WordSpec({
  "GPropertyDelegate" `when` {
    "given no prefix" should {
      "still work" {
        val project = ProjectBuilder.builder().build().also {
          it.extensions.extraProperties["target"] = "true"
        }

        val target by project.lazyProperty()
        target shouldBe "true"
      }
    }
    "given various keys" should {
      checkAll(Arb.stringPattern("[A-Za-z0-9_. ]{0,100}")) { prefix ->
        "support prop-like keys" {
          val expectedKey = prefix.trim().removeSuffix(".")
            .takeIf(String::isNotBlank)?.plus(".target") ?: "target"
          info("prefix=[$prefix], expectedKey=[$expectedKey]")
          val project = ProjectBuilder.builder().build().also {
            it.extensions.extraProperties[expectedKey] = "true"
          }

          val target by project.lazyProperty(
            prefix = prefix,
            converter = String::toBoolean
          )
          target shouldBe true
        }
        "return null if the property does not exist" {
          info("prefix=[$prefix]")
          val project = ProjectBuilder.builder().build()

          val target by project.lazyProperty(
            prefix = prefix,
          )
          target shouldBe null
        }
      }
    }
  }
})
