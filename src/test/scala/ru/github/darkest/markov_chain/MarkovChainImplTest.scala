package ru.github.darkest.markov_chain

import cats.tests.StrictCatsEquality
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.collection.immutable.HashMap

class MarkovChainImplTest extends AnyFlatSpec with StrictCatsEquality {

  it should "create MarkovChainImpl with apply()" in {
    val impl = MarkovChainImpl("some text")
    val impl2 = new MarkovChainImpl(Seq("some text"))
  }

  "MarkovChainImpl" should "be correctly initialized given simple text" in {
    val text = "Ехал грека через реку"
    val impl = new MarkovChainImpl(text = text.split(' '))
    impl.dict shouldBe Map("ехал" -> Seq("грека"), "грека" -> Seq("через"), "через" -> Seq("реку"))
    impl.chainsDict shouldBe Map("ехал" -> Map("грека" -> 1d), "грека" -> Map("через" -> 1d), "через" -> Map("реку" -> 1d))
  }

  val test = "MarkovChainImpl" should "be correctly initialized given slightly more complex text" in {
    val text = "Ехал грека через реку, видит грека — в реке рак, сунул грека руку в реку, рак за руку греку цап"
    val impl = new MarkovChainImpl(text = text.split(' '))
    val expectedDict: Map[String, Seq[String]] = Seq("ехал" -> "грека", "грека" -> "через", "через" -> "реку", "реку" -> "видит", "видит" -> "грека",
    "грека" -> "в","в" -> "реке","реке" -> "рак","рак" -> "сунул","сунул" -> "грека", "грека" -> "руку", "руку" -> "в",
    "в" -> "реку", "реку" -> "рак", "рак" -> "за", "за" -> "руку", "руку" -> "греку", "греку" -> "цап")
      .groupBy(_._1)
      .map{case (k, tuples) => k -> tuples.foldLeft(Seq[String]())((acc, el) => acc :+ el._2)}
    val expectedChains = Map(
    "ехал" -> HashMap("грека" -> 1d),
    "грека" -> HashMap("через" -> 1d/3 , "в" -> 1d/3, "руку" -> 1d/3),
    "через" -> HashMap("реку" -> 1d),
    "реку" -> HashMap("видит" -> 1d/2, "рак" -> 1d/2),
    "видит" -> HashMap("грека" -> 1d),
    "в" -> HashMap("реке" -> 1d/2, "реку" -> 1d/2),
    "реке" -> HashMap("рак" -> 1d),
    "рак" -> HashMap("сунул" -> 1d/2, "за" -> 1d/2),
    "сунул" -> HashMap("грека" -> 1d),
    "руку" -> HashMap("в" -> 1d/2, "греку" -> 1d/2),
    "за" -> HashMap("руку" -> 1d),
    "греку" -> HashMap("цап" -> 1d))

    assert(impl.dict === expectedDict)
    assert(impl.chainsDict === expectedChains)
    impl.chainsDict === expectedChains shouldBe true
  }

  "initializeDict" should "produce empty map when empty string is supplied" in {
    val text = "Ехал грека через реку"
    val impl = new MarkovChainImpl(text = Seq())
    impl.dict === Map[String, Seq[String]]() shouldBe true
    impl.chainsDict === Map[String, Map[String, Double]]() shouldBe true
  }

}
