package ru.github.darkest.markov_chain

import scala.annotation.tailrec

class MarkovChainImpl(text: Seq[String]) extends MarkovChain {

  type Text = Seq[String]
  type TextList = List[String]
  type ChainDict = Map[String, Seq[String]]

  private def initializeDict(text: Text): ChainDict = {
    @tailrec
    def loop(leftToParse: TextList, dict: ChainDict): ChainDict = {
      leftToParse match {
        case first :: second :: tail =>  loop(second :: tail, dict + (first -> (dict.getOrElse[Seq[String]](first, Seq[String]()) :+ second)))
        case _ => dict
      }
    }
    loop(text.map(Utils.sanitizeText).filter(_.nonEmpty).toList, Map())
  }

  private def buildChainsWithProbabilities(pairsForWord: (String, Seq[String])): (String, Map[String, Double]) = {
    val first = pairsForWord._1
    val secondsCount = pairsForWord._2.size.doubleValue
    val secondsOccurrencesCountMap: Map[String, Int] = pairsForWord._2.groupBy(identity).map{case (word, words) => word -> words.size }
    val result = first -> secondsOccurrencesCountMap.map{case (second, count) => second -> count / secondsCount}
    result
  }

  lazy val dict: Map[String, Seq[String]] = initializeDict(text)

  lazy val chainsDict: Map[String, Map[String, Double]] =
    dict.map(buildChainsWithProbabilities)

  override def generate(textLength: Int): String = ???
}

object MarkovChainImpl {
  def apply(text: String): MarkovChainImpl = new MarkovChainImpl(text.split(' ').toVector)
}
