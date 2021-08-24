package ru.github.darkest

import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import ru.github.darkest.markov_chain.Utils

class UtilsTest extends AnyFlatSpecLike{

  "sanitize" should "remove every char except alphanumeric" in {
    val testString = "Ехал грека через реку, видит грека — в реке рак, сунул грека руку в реку, рак за руку греку цап"
    val sanitized = Utils.sanitizeText(testString)
    sanitized shouldBe "ехал грека через реку видит грека в реке рак сунул грека руку в реку рак за руку греку цап"
  }

}
