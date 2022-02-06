package dev.petuska.gradle.kotlin.delegates

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import org.gradle.api.Project
import org.gradle.api.internal.provider.MissingValueException
import org.gradle.api.model.ObjectFactory
import org.junit.jupiter.api.assertThrows
import javax.inject.Inject

class WithGLazyTest : WordSpec() {
  init {
    "WithGLazy" `when` {
      "given mock project with an extension implementing it" should {
        withProject {
          createExtension()

          "support nullable delegated reads" {
            extensions.configure<TestExtension> {
              _property.set(null as String?)

              baseProperty shouldBe null
            }
          }
          "support nullable delegated writes" {
            extensions.configure<TestExtension> {
              _property.set("OK" as String?)
              _property.isPresent shouldBe true

              baseProperty = null
              _property.isPresent shouldBe false
            }
          }
          "support delegated reads" {
            extensions.configure<TestExtension> {
              _property.set(null as String?)
              baseProperty shouldBe null

              _property.set("OK")
              baseProperty shouldBe "OK"
            }
          }
          "support delegated writes" {
            extensions.configure<TestExtension> {
              _property.set(null as String?)
              baseProperty shouldBe null

              baseProperty = "OK"
              _property.get() shouldBe "OK"
            }
          }
          "throw an error on delegated nullable reads on strict mode GLazy" {
            extensions.configure<TestExtension> {
              _provider.set(null as String?)
              assertThrows<MissingValueException> {
                strictProvider shouldBe null
              }
            }
          }
          "throw an error on delegated nullable reads on strict mode GMutableLazy" {
            extensions.configure<TestExtension> {
              _property.set(null as String?)
              assertThrows<MissingValueException> {
                strictProperty shouldBe null
              }
            }
          }
        }
      }
    }
  }

  private fun Project.createExtension(
    extensionName: String = "testExtension",
    init: (TestExtension.() -> Unit)? = null,
  ) {
    val extension = extensions.create(extensionName, TestExtension::class.java)
    init?.invoke(extension)
  }

  @Suppress("PropertyName", "Unused", "CanBeParameter")
  abstract class TestExtension(
    @Inject
    val factory: ObjectFactory,
  ) : WithGLazy {
    val _provider: GMutableLazy<String> = factory.property(String::class.java)
    val _property: GMutableLazy<String> = factory.property(String::class.java)

    val baseProvider by _provider
    val strictProvider by (_provider as GLazy<String>).strict()
    var baseProperty by _property
    var strictProperty by _property.strict()
  }
}
