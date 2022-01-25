import dev.petuska.gradle.kotlin.delegates.GEnvironmentDelegate
import dev.petuska.gradle.kotlin.delegates.GEnvironmentDelegate.Companion.separators
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.checkAll

class GEnvironmentDelegateTest : WordSpec({
  "various env keys" should {
    fun String.asGetEnv() = { it: String ->
      if (it == this) {
        println("✅ Key $it did match expected $this")
        "true"
      } else {
        println("❌ Key $it did not match expected $this")
        null
      }
    }
    checkAll(Arb.stringPattern("[A-Za-z0-9_.]{0,100}")) { prefix ->
      "support prop-like key" {
        println("Prefix: $prefix")
        val expectedKey = prefix.lowercase().replace(separators, ".")
          .removeSuffix(".").takeIf(String::isNotBlank)?.plus(".target") ?: "target"
        val target by GEnvironmentDelegate(
          prefix = prefix,
          getEnv = expectedKey.asGetEnv(),
          converter = { it }
        )
        target shouldBe "true"
      }
      "support env-like key" {
        println("Prefix: $prefix")
        val expectedKey = prefix.uppercase().replace(separators, "_")
          .removeSuffix("_").takeIf(String::isNotBlank)?.plus("_TARGET") ?: "TARGET"
        val target by GEnvironmentDelegate(
          prefix = prefix,
          getEnv = expectedKey.asGetEnv(),
          converter = { it }
        )
        target shouldBe "true"
      }
    }
  }
})
