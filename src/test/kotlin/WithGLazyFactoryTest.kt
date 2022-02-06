package dev.petuska.gradle.kotlin.delegates

import io.kotest.core.spec.style.WordSpec
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

class WithGLazyFactoryTest : WordSpec() {
  init {
    "WithGLazyFactory" `when` {
//      "given mock project with an extension implementing it" should {
//        "support gradle property fallbacks" {
//          // TODO figure out how to get gradle mock project to register Gradle properties
//          val key = "myGradleProperty"
//          info("key=$key")
//          withSystemProperty("org.gradle.property.$key", key) {
//            withProject {
//              createExtension(gradlePropertyKey = key)
//              info("Gradle Properties: ${properties.map { (k, v) -> "$k=$v" }.joinToString(", ")}")
//
//              extensions.configure<TestExtensionWithFactory> {
//                _property.set(null as String?)
//                orSystemProperty shouldBe key
//              }
//            }
//          }
//        }
//        "support system property fallbacks" {
//          // TODO figure out how to get gradle mock project to register System properties
//          val key = "myGradleProperty"
//          info("key=$key")
//          withSystemProperty(key, key) {
//            withProject {
//              createExtension(systemPropertyKey = key)
//              info("System Properties: ${System.getProperties().map { (k, v) -> "$k=$v" }.joinToString(", ")}")
//
//              extensions.configure<TestExtensionWithFactory> {
//                _property.set(null as String?)
//                orSystemProperty shouldBe key
//              }
//            }
//          }
//        }
//        "support environment variable fallbacks" {
//          // TODO figure out how to get gradle mock project to register environment variables
//          val key = "MY_GRADLE_PROPERTY"
//          info("key=$key")
//          withSystemProperty(key, key) {
//            withProject {
//              createExtension(envKey = key)
//              info("Environment Variables: ${System.getenv().map { (k, v) -> "$k=$v" }.joinToString(", ")}")
//
//              extensions.configure<TestExtensionWithFactory> {
//                _property.set(null as String?)
//                orEnv shouldBe key
//              }
//            }
//          }
//        }
//      }
    }
  }

  private fun Project.createExtension(
    gradlePropertyKey: String = "myProperty",
    systemPropertyKey: String = "my.property",
    envKey: String = "MY_PROPERTY",
    extensionName: String = "testExtension",
    init: (TestExtensionWithFactory.() -> Unit)? = null,
  ) {
    val extension = extensions.create(
      extensionName,
      TestExtensionWithFactory::class.java,
      gradlePropertyKey,
      systemPropertyKey,
      envKey
    )
    init?.invoke(extension)
  }

  @Suppress("PropertyName", "Unused", "CanBeParameter")
  abstract class TestExtensionWithFactory(
    gradlePropertyKey: String,
    systemPropertyKey: String,
    envKey: String,
    factory: ObjectFactory,
  ) : WithGLazyTest.TestExtension(factory), WithGLazyFactory {
    val orGradleProperty by _property orGradleProperty gradlePropertyKey
    val orSystemProperty by _property orGradleProperty systemPropertyKey
    val orEnv by _property orEnv envKey
    val orGradleOrSystemOrEnv by _property orGradleProperty
      gradlePropertyKey orSystemProperty
      systemPropertyKey orEnv envKey
  }
}
