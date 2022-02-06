package local.sandbox

import dev.petuska.gradle.kotlin.delegates.WithGLazyFactory
import dev.petuska.gradle.kotlin.delegates.gMutableLazy
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.provideDelegate

open class TestPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.extensions.create<TestExtension>("testExtension")
  }

  abstract class TestExtension : WithGLazyFactory {
    private val _emptyProperty = gMutableLazy<String>()
    val _property = gMutableLazy<String>()
    var baseProperty by _property
    val orEnv by _emptyProperty orEnv "HOME"
    val orSystem by _emptyProperty orSystemProperty "user.home"
    val orGradle by _emptyProperty orGradleProperty "version"
    val orFallbackChain by _emptyProperty orGradleProperty "none" orSystemProperty "none" orEnv "HOME"
  }
}
